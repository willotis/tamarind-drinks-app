# Mock Server

The mock server provides a simulated backend for the Tamarind Drinks app, allowing for development and testing without a live API.

## How it Works

When `USE_MOCK_SERVER` is enabled in the app's build configuration, the networking layer is intercepted. Instead of making real HTTP requests, the app reads responses from local JSON files.

## API Specification

The API specification is not yet available. It will be located at `mock-server/api-spec.yaml`.

## Adding New Mock Responses

To add a new mock response, create a JSON file in the `app/src/debug/assets/mock_responses` directory. The filename should correspond to the API endpoint it mocks.
