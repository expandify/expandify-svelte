

async function sleep(ms) {
  return await new Promise(resolve => setTimeout(resolve, ms));
}

export async function get({ params }) {

  await sleep(5000);

  return {
    status: 200
  };

}