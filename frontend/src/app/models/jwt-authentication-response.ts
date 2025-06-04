export interface JwtAuthenticationResponse {
  accessToken: string;
  tokenType: string;
  message?: string;
}
