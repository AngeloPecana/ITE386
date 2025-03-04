import requests

API_URL = "https://api.weather.cloud"
location = "New York"
API_KEY = "YOUR_API_KEY"

try:
    response = requests.get(f"{API_URL}/forecast?location={location}&key={API_KEY}")
    
    if response.status_code == 200:
        weather_data = response.json()

        print(f"Weather forecast for {location}:")
        for forecast in weather_data.get("forecast", []):
            date = forecast.get("date", "N/A")
            temperature = forecast.get("temperature", "N/A")
            description = forecast.get("description", "N/A")
            print(f"{date}: {temperature}°C - {description}")
    else:
        print(f"Failed to retrieve weather forecast. Status code: {response.status_code}")
except requests.exceptions.RequestException as e:
    print(f"An error occurred: {e}")
