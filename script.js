const GOOGLE_PLACES_API_KEY = "YOUR_GOOGLE_PLACES_API_KEY";
const OPENWEATHER_API_KEY = "YOUR_OPENWEATHER_API_KEY";
const GOOGLE_MAPS_API_KEY = "YOUR_GOOGLE_MAPS_API_KEY";

const container = document.querySelector(".container");
const chatsContainer = document.querySelector(".chats-container");
const promptForm = document.querySelector(".prompt-form");
const promptInput = promptForm.querySelector(".prompt-input");
const locationInput = document.querySelector("#location");
const mapContainer = document.querySelector("#map");
const weatherContainer = document.querySelector("#weather-info");

// Function to get Google Places recommendations
const fetchPlaces = async (query) => {
    const API_URL = `https://maps.googleapis.com/maps/api/place/textsearch/json?query=${query}&key=${GOOGLE_PLACES_API_KEY}`;

    try {
        const response = await fetch(API_URL);
        if (!response.ok) throw new Error("API response error");
        
        const data = await response.json();
        return data.results ? data.results.map(place => place.name).join(", ") : "No places found.";
    } catch (error) {
        console.error("Error fetching places:", error);
        return "Failed to fetch place recommendations.";
    }
};

// Function to get weather details
const fetchWeather = async (city) => {
    const API_URL = `https://api.openweathermap.org/data/2.5/weather?q=${city}&appid=${OPENWEATHER_API_KEY}&units=metric`;

    try {
        const response = await fetch(API_URL);
        if (!response.ok) throw new Error("API response error");

        const data = await response.json();
        return data.weather ? `Weather in ${city}: ${data.weather[0].description}, ${data.main.temp}°C` : "Weather data unavailable.";
    } catch (error) {
        console.error("Error fetching weather:", error);
        return "Failed to fetch weather data.";
    }
};

// Function to initialize Google Map
const initMap = (latitude, longitude) => {
    if (!mapContainer) {
        console.error("Map container is missing.");
        return;
    }
    
    const map = new google.maps.Map(mapContainer, {
        center: { lat: latitude, lng: longitude },
        zoom: 10
    });

    new google.maps.Marker({
        position: { lat: latitude, lng: longitude },
        map: map
    });
};

// Function to handle user input
const handleFormSubmit = async (e) => {
    e.preventDefault();
    const userQuery = promptInput.value.trim();
    const userLocation = locationInput.value.trim();

    if (!userQuery || !userLocation) return;

    promptInput.value = "";
    locationInput.value = "";

    const userMsgDiv = document.createElement("div");
    userMsgDiv.classList.add("message", "user-message");
    userMsgDiv.innerHTML = `<p class="message-text">${userQuery} for ${userLocation}</p>`;
    chatsContainer.appendChild(userMsgDiv);

    setTimeout(async () => {
        const botMsgDiv = document.createElement("div");
        botMsgDiv.classList.add("message", "bot-message");
        botMsgDiv.innerHTML = `<img src="./travel-guide.jpeg" class="avatar"><p class="message-text">Fetching details...</p>`;
        chatsContainer.appendChild(botMsgDiv);

        const placesData = await fetchPlaces(`${userQuery} in ${userLocation}`);
        const weatherData = await fetchWeather(userLocation);

        botMsgDiv.querySelector(".message-text").textContent = `🏨 Places: ${placesData}\n🌤️ Weather: ${weatherData}`;

        try {
            const geoResponse = await fetch(`https://maps.googleapis.com/maps/api/geocode/json?address=${userLocation}&key=${GOOGLE_MAPS_API_KEY}`);
            if (!geoResponse.ok) throw new Error("Failed to fetch geolocation");

            const geoData = await geoResponse.json();
            if (geoData.results && geoData.results.length > 0) {
                const { lat, lng } = geoData.results[0].geometry.location;
                initMap(lat, lng);
            }
        } catch (error) {
            console.error("Error fetching geolocation:", error);
        }
    }, 600);
};

// Attach event listener for form submission
promptForm.addEventListener("submit", handleFormSubmit);