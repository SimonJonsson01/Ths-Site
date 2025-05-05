# Build stage
FROM node:20 AS builder
WORKDIR /app

# Copies and installs all packages and dependencies.
COPY package.json package-lock.json* ./
RUN npm install

COPY . .
# Copies all project files and builds the frontend in the container.
RUN npm run build

# Production stage
FROM nginx:1.27.1 AS runner
COPY --from=builder /app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
