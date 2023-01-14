export class ResumePoint {
  full_played!: boolean;
  resume_position_ms!: number;

  private constructor() {
  }

  public static fromResumePointObject(rp: SpotifyApi.ResumePointObject): ResumePoint {
    const resumePoint = new ResumePoint();
    resumePoint.full_played = rp.full_played;
    resumePoint.resume_position_ms = rp.resume_position_ms;
    return resumePoint;
  }
}