FROM node:20-alpine AS build
WORKDIR /app
RUN corepack enable && corepack prepare pnpm@9.15.4 --activate
COPY package.json package-lock.json* ./
RUN pnpm install --no-frozen-lockfile
COPY . .
ARG VITE_API_URL=/api
ENV VITE_API_URL=$VITE_API_URL
ARG VITE_OIDC_ISSUER
ARG VITE_OIDC_CLIENT_ID=web-client
ENV VITE_OIDC_ISSUER=$VITE_OIDC_ISSUER
ENV VITE_OIDC_CLIENT_ID=$VITE_OIDC_CLIENT_ID
RUN pnpm run build

FROM nginx:1.27-alpine
COPY --from=build /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
