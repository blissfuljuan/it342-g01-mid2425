June 4, 2025

**What I learned today:**  
I learned how to use the basic Git commands like `git init`, `git commit`, and `git push`. I now understand how to track changes in a local repository and push them to GitHub.

**What I'm still confused about:**  
I'm still unsure when to use the staging area vs committing directly. Also, how do I remove a file that was accidentally added?


June 4, 2025

**What I learned today:**  
I learned how to check the repo status with `git status`, and now I understand the difference between staged and committed files.

**What I'm still confused about:**  
How to undo a commit without deleting local changes.

June 4, 2025

**What I learned today:**  
I now understand how to fork repositories and push to my own forked version.

**What I'm still confused about:**  
How do I undo a commit without deleting my changes?

June 13, 2025

**What I learned today:**  
I now understand how google and github integration works

**What I'm still confused about:**  
Im still a bit confused how to integrate github through google using security

June 19, 2025

**What I learned today:**  
I learned the different types of integration and requirement analysis of a system

**What I'm still confused about:**  
How do you integrate those different integration architectures and making them scalable towards other systems?

[Date: 06-20-2025]
 
1. What did I accomplish yesterday?
 
I added the necessary Spring Boot dependencies: Web, OAuth2 Client, Thymeleaf.
 
I started working on the controller to manage Google OAuth login and callback.
 
I successfully set up the application.properties with my OAuth client ID and secret.
 
 
2. What will I do today?
 
I will complete the controller code to exchange the auth code for an access token.
 
I will call the People API (people/me/connections) and display contact names and emails in a Thymeleaf view.

Fix Controller and Service Setup as well as the necessary import dependencies in the pom.xml
 
I will test the login flow and data display while handling error with contacts
 
 
3. Any blockers or challenges?
 
Still validating that the access token works properly with the People API.
 
Small issue with handling error states if a user cancels authorization.

<<<<<<< HEAD
[Date: 06-26-2025]
1. What did I accomplish yesterday?

Finalized the frontend directory structure using React with Tailwind CSS and Vite.

Verified folder structure matched system design documentation.

Reviewed UI/UX screenshots and matched the login design accordingly.

. What will I do today?

Implement the RegisterForm.jsx and finish connecting it to the backend auth API.

Begin building Dashboard.jsx for customers and LoanApplication.jsx based on the ERD and functional specs.

Set up the routing structure using react-router-dom and integrate Header.jsx and Navigation.jsx.

3. Any blockers or challenges?

Ensuring real-time backend integration with Spring Boot due to CORS and JWT auth setup.

Need more clarity on some field names for loan applications and payments in the backend API.
=======
[Date: 06-23-2025]//
 
1. What did I accomplish yesterday?
 
Continued working on my google contacts integration especially the controller and googlecontact service site to make sure 
that the access tokens can be called without issues as well as improving ui design for user friendly interaction in the templates side
 
2. What will I do today?
 
finished up setting the google contacts successfully by adding crud operations and redirecting it to my google contacts site 
 
3. Any blockers or challenges?
 
Still validating that the access token works properly with the People API.
Still  Configuring  springboot and pom.xml depedencies making sure some java and google api depencies
are correct to avoid running errors..
>>>>>>> dd84283dd183490b7c1e2d19e84961205130c48e


