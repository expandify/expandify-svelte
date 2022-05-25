### Build Step
# pull the Node.js Docker image
FROM node:16-alpine as builder

# change working directory
WORKDIR /app

# copy the package.json files from local machine to the workdir in container
COPY package*.json ./

# run npm install in our local machine
RUN npm ci

# copy the generated modules and all other files to the container
COPY . .

# build the application
RUN npm run build

### Serve Step
# pull the Node.js Docker image
FROM node:16-alpine

# change working directory
WORKDIR /app

RUN echo "['SIGINT', 'SIGTERM', 'SIGQUIT'].forEach(signal => process.on(signal, () => process.exit())); await import('./index.js')" > docker-entrypoint.js
COPY --from=builder /app/package*.json .
RUN npm ci --production --ignore-scripts

COPY --from=builder /app/build .

# our app is running on port 3000 within the container, so need to expose it
EXPOSE 3000

# the command that starts our app
CMD ["node", "./docker-entrypoint.js"]