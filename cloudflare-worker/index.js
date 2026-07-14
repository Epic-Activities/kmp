const CLIENT_ID = "265079";
const CLIENT_SECRET = "af61a6b983c27cb9eb0db21fada6a3056fd88df5";
const STRAVA_TOKEN_URL = "https://www.strava.com/api/v3/oauth/token";
const STRAVA_ACTIVITIES_URL = "https://www.strava.com/api/v3/athlete/activities";

const CORS = {
  "Access-Control-Allow-Origin": "*",
  "Access-Control-Allow-Methods": "GET, POST, OPTIONS",
  "Access-Control-Allow-Headers": "Content-Type, Authorization",
};

export default {
  async fetch(request) {
    if (request.method === "OPTIONS") {
      return new Response(null, { status: 204, headers: CORS });
    }

    const { pathname, searchParams } = new URL(request.url);

    // POST /exchange  — auth code → tokens
    if (pathname === "/exchange" && request.method === "POST") {
      const { code } = await request.json();
      const res = await fetch(STRAVA_TOKEN_URL, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          client_id: CLIENT_ID,
          client_secret: CLIENT_SECRET,
          code,
          grant_type: "authorization_code",
        }),
      });
      const data = await res.json();
      return Response.json(data, { status: res.status, headers: CORS });
    }

    // POST /refresh  — refresh_token → new tokens
    if (pathname === "/refresh" && request.method === "POST") {
      const { refresh_token } = await request.json();
      const res = await fetch(STRAVA_TOKEN_URL, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          client_id: CLIENT_ID,
          client_secret: CLIENT_SECRET,
          refresh_token,
          grant_type: "refresh_token",
        }),
      });
      const data = await res.json();
      return Response.json(data, { status: res.status, headers: CORS });
    }

    // GET /activities  — proxies to Strava with the Bearer token
    if (pathname === "/activities" && request.method === "GET") {
      const auth = request.headers.get("Authorization");
      const perPage = searchParams.get("per_page") ?? "100";
      const page = searchParams.get("page") ?? "1";
      const res = await fetch(
        `${STRAVA_ACTIVITIES_URL}?per_page=${perPage}&page=${page}`,
        { headers: { Authorization: auth } }
      );
      const data = await res.json();
      return Response.json(data, { status: res.status, headers: CORS });
    }

    return new Response("Not Found", { status: 404, headers: CORS });
  },
};
