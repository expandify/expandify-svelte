defmodule ExportifyLocalStorage.LibraryStorage do
  @moduledoc """
     Represents a storage process in form of a GenServer. This process will kill itself after a specified expire time.
    The data in the storage is a map with a list of subscribers and items.
    All process ids in the subscriber list will be notified whenever an update on the items occurs.
  """
  use GenServer

  ######## Client Api ########

  @doc """
     Starts the Genserver will be started and its PID will be returned.
  """
  def start_link(expires_in) do
    GenServer.start_link(__MODULE__, expires_in)
  end

  @doc """
     Starts the Genserver unlinked from the supervisior tree and its PID will be returned.
  """
  def start(expires_in) do
    GenServer.start(__MODULE__, expires_in)
  end

  @doc """
     Returns the Item list from the data.
  """
  def get_items(component_pid) do
    GenServer.call(component_pid, :get_items)
  end

  @doc """
    Returns all of the data in the storage.
  """
  def get(component_pid) do
    GenServer.call(component_pid, :get)
  end

  @doc """
    Adds an item to the list of Items in the storage.
    Notifies all subscribers that an update occurred.
  """
  def update_item(component_pid, item) do
    GenServer.cast(component_pid, {:update_item, item})
    notify_subscribers(component_pid)
  end

  @doc """
    Adds the list items (parameter) to the list of items in the storage.
    Notifies all subscribers that an update occurred.
  """
  def update_items(component_pid, items) do
    GenServer.cast(component_pid, {:update_items, items})
    notify_subscribers(component_pid)
  end

  defp notify_subscribers(component_pid) do
    state = get(component_pid)
    state.subscribers
    |> Enum.each(&(send(&1, {:update, state.items, self()})))
    :ok
  end

  @doc """
    Adds an subscriber to the storage process.
  """
  def subscribe(component_pid, subscriber_pid) do
    GenServer.cast(component_pid, {:subscribe, subscriber_pid})
  end


  ######## Server (callbacks) ########

  @doc """
    Initializes the storage.
    Sets a expire time, that causes a the process to be killed after it expires.
  """
  @impl true
  def init(expires_in) do
    Process.send_after(self(), :harakiri, expires_in * 1000)
    {:ok, %{subscribers: [], items: []}}
  end

  @doc """
    Callback function to retriev all data or all items from the storage
  """
  @impl true
  def handle_call(:get, _from, state) do
    {:reply, state, state}
  end

  @impl true
  def handle_call(:get_items, _from, state) do
    {:reply, state.items, state}
  end

  @doc """
    Callback to add an item to the item list in the storage.
    Callback to add an item list (parameter) to the item list in the storage.
    Callback to add a new subscriber to the storage.
  """
  @impl true
  def handle_cast({:update_item, item}, state) do
    new_state = Map.update(state, :items, [], fn (current) -> (current ++ [item]) end)
    {:noreply, new_state}
  end

  @impl true
  def handle_cast({:update_items, items}, state) do
    new_state = Map.update(state, :items, [], fn (current) -> (current ++ items) end)
    {:noreply, new_state}
  end

  @impl true
  def handle_cast({:subscribe, subscriber_pid}, state) do
    new_state = Map.update(state, :subscribers, [], fn (subscribers) -> ([subscriber_pid | subscribers]) end)
    {:noreply, new_state}
  end

  @doc """
    Callback to the expiration time.
    When this function is called the storage process will end itself.

    Callback to any message send to this process.
    Will result in nothing but is needed to not block the message queue.
  """
  @impl true
  def handle_info(:harakiri, _state) do
    exit(:normal)
    {:noreply, %{}}
  end

  @impl true
  def handle_info(_msg, state) do
    {:noreply, state}
  end

end
