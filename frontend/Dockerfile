# FROM nginx
# COPY /dist/movie-booking-app/ /usr/share/nginx/html
# Build Angular app
FROM node:20 as build
WORKDIR /app
COPY . .
RUN npm install && npm run build

# Serve using nginx
FROM nginx:alpine
COPY --from=build /app/dist/movie-booking-app /usr/share/nginx/html
