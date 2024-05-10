FROM node:20.11.1-alpine as build-stage

WORKDIR /frontend

# ENV PATH /frontend/node_modules/.bin:$PATH

COPY package.json .

RUN npm install

COPY . .

# CMD ["npm", "run", "serve"]

RUN npm run build -- --prod --output-path=/dist

FROM nginx:stable-alpine as production-stage

COPY --from=build-stage /frontend/dist /usr/share/nginx/html

COPY ./nginx/default.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

# CMD [ "nginx", "-g", "daemon off;"]