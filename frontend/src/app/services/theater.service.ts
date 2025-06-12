import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Theater } from '../models/theater.model';

const BASE_URL = 'http://localhost:9090/api/theaters';

@Injectable({
  providedIn: 'root'
})
export class TheaterService {
  constructor(private http: HttpClient) {}

  getAllTheaters(): Observable<Theater[]> {
    return this.http.get<Theater[]>(BASE_URL);
  }
}
