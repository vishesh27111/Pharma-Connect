FROM node:18.18

RUN pwd

WORKDIR /app

RUN pwd

COPY / /app

RUN npm install

EXPOSE 4200

ENTRYPOINT ["npm", "run", "start"]
