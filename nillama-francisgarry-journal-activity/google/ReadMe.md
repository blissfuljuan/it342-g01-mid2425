🪜 Steps and Flow
1. User Visits Homepage
Route: /

View: index.html

Contains a "Login with Google" button which initiates the OAuth2 login flow.

2. OAuth2 Authentication
After login, Spring Security provides an OAuth2AuthenticationToken to the controller.

3. Accessing Google People API
Route: /contacts

The controller checks if the user is authenticated.

If authenticated:

Retrieves the OAuth2AuthorizedClient using OAuth2AuthorizedClientService.

Extracts the access token.

Sends a GET request to https://people.googleapis.com/v1/people/me/connections with the following query:

r
Copy
Edit
?personFields=names,emailAddresses,phoneNumbers
4. Using RestTemplate
A RestTemplate is used to send the authenticated GET request.

The response body (JSON containing contacts) is retrieved.

5. Displaying the Contacts
The JSON response is added to the Model as contactsJson.

View: contacts.html renders the contacts (raw JSON or parsed version).