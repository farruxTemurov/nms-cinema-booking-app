import { Theater } from './theater.model';

export interface Movie {
  id?: number;
  title: string;
  language: string;
  genre: string;
  rating: number;
 theater?: Partial<Theater>; 
}
