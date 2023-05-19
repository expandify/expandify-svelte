package embeds

import (
	"expandify-api/pkg/expandify"
	"time"
)

type Sync struct {
	Type    string               `json:"type"`
	Status  expandify.SyncStatus `json:"status"`
	Error   error                `json:"error"`
	Current int                  `json:"current"`
	Max     int                  `json:"max"`
	Start   time.Time            `json:"start"`
	End     time.Time            `json:"end"`
}

func NewSync(sync *expandify.Sync) *Sync {
	return &Sync{
		Type:    sync.Type,
		Status:  sync.Status,
		Error:   sync.Error,
		Current: sync.Current,
		Max:     sync.Max,
		Start:   sync.Start,
		End:     sync.End,
	}
}

func (s Sync) Convert() *expandify.Sync {
	return &expandify.Sync{
		Type:    s.Type,
		Status:  s.Status,
		Error:   s.Error,
		Current: s.Current,
		Max:     s.Max,
		Start:   s.Start,
		End:     s.End,
	}
}
