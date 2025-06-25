June 4, 2025
Today I learned how to fork and clone a GitHub repository.

One thing I'm still confused about: What is the difference between `pull` and `fetch`?

June 5, 2025
Learned how to use `git add` and `git commit`.

Still confused about what "staging" really means.

June 6, 2025
Pushed changes to GitHub for the first time.

Still a bit unsure when to use `git pull`.

June 16, 2025
Explored how to create and switch branches using git branch and git checkout.

Still figuring out how merging works and what to do when there are conflicts.

June 20, 2025

Started working with git merge today. Got a better understanding of how it combines branches, but I ran into a conflict during the merge. Had to manually resolve it. I think I’m starting to get the hang of it, but it’s still a bit tricky.

Also, I learned that git pull is a combination of git fetch followed by a git merge. That cleared up some confusion!

## Read Me Part 1: View-Only Integration with Google Contacts

I created a new project in Google Cloud Console and enabled the People API.

I set up OAuth 2.0 credentials by creating a Web Client ID and Client Secret.

I configured the OAuth consent screen, added the scope https://www.googleapis.com/auth/contacts.readonly, and included my Google account as a test user.

In my Spring Boot project, I added dependencies for Spring Web, Spring Security OAuth2 Client, and Thymeleaf.

I added my Google credentials and scopes to application.properties.

I created a controller that handles login and retrieves an access token after authentication.

Using RestTemplate, I called the Google People API to fetch contact names and email addresses.

I used Thymeleaf templates to build a simple UI that shows a login button (index.html) and a list of contacts (contacts.html).

I tested the app by running it locally at http://localhost:8080, authenticated with Google, and verified that my contacts were retrieved and displayed correctly.

I pushed the complete source code to GitHub, took a screenshot of the contacts page, and added this short report.

June 