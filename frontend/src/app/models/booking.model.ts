export interface Booking {
    id: number;
    bookingTime: string;
    user: {
        id: number;
        fullName: string;
        email: string;
    };
    movie: {
        id: number;
        title: string;
    };
    theater: {
        id: number;
        name: string;
    };
}
