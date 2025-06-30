## Git Journal

### June 3, 2025  
**What I Learned Today:**  
I learned the basics of git init, git add, and git commit.  

**What I'm Still Confused About:**  
When to use git fetch vs git pull?

---

### June 4, 2025  
**What I Learned Today:**  
I practiced cloning repositories and pushing to GitHub.  

**What I'm Still Confused About:**  
What does 'origin' mean in Git?

---

### June 5, 2025  
**What I Learned Today:**  
I learned how to make multiple commits and push to my remote repository.  

**What I'm Still Confused About:**  
How to resolve merge conflicts?

**[Date: 06-20-2025]**
1. What did I accomplish yesterday?
Cloned the project repository it342-g01-mid2425 from GitHub.
Set up the development environment locally.
Reviewed the project structure and dependencies.

2. What will I do today?
Start working on the assigned feature/module (e.g., implement login validation).
Set up the database connection and seed initial data if needed.
Push initial changes to a new feature branch.

3. Any blockers or challenges?
Need clarification on API integration details from the team.
Encountered an error while running the project locally (missing environment variables).

**[Date: 06-23-2025]**
1. What did I accomplish yesterday?

Fixed the Google Contacts integration for contact updates.
Resolved the issue where contact names were appearing as "No Name".
Updated GoogleContactsService.java to correctly use givenName and displayName when adding or updating contacts.
Verified that edit and update functionalities reflect changes in actual Google Contacts.

2. What will I do today?

Refactor and clean up the codebase (remove redundancies, improve error handling).
Ensure the UI/UX is consistent across all views (add/edit/list).
Implement support for additional fields (e.g., birthday, organization).
Conduct end-to-end testing and push to GitHub.

3. Any blockers or challenges?

Still encountering occasional Whitelabel Error Page with unclear logs.
Need to confirm scope for additional fields with the team.
Might require adjustments for Google People API quota or permissions.

**[Date: 06-25-2025]**
1. What did I accomplish yesterday?
Successfully integrated additional contact fields including birthday, organization, and job title into both the backend and UI.
Refactored the codebase for improved readability and maintainability.
Fixed layout inconsistencies across add, edit, and list contact views.
Performed end-to-end testing for add/edit/delete operations.

2. What will I do today?

Address any remaining issues causing intermittent Whitelabel error pages.
Optimize API error messages for better frontend feedback.
Add loading indicators and error states in the UI.
Push all changes to GitHub and ensure deployment is stable.
Review API quota and scopes to ensure smooth interaction with Google People API.

3. Any blockers or challenges?

Whitelabel error still occurs unpredictably with minimal log output.
May need to create a fallback error handler to capture uncaught exceptions.
Waiting on confirmation about multi-value field behavior (emails/phones) before finalizing UI logic.

**[Date: 06-30-2025]**

1. What did I accomplish yesterday?

Investigated and partially resolved causes of the intermittent Whitelabel error pages.
Implemented basic fallback error handling to catch uncaught exceptions globally.
Improved frontend UX by adding loading indicators to the contact views.
Refined error messages from the backend to be more descriptive and user-friendly.
Committed and pushed latest changes to GitHub for backup and team access.

2. What will I do today?

Finalize and test fallback error handling to ensure graceful failures.
Complete UI logic for multi-value fields (emails, phone numbers) pending team confirmation.
Review and clean up exception handling in all controller methods.
Revisit OAuth scopes and Google API quota dashboard to check for usage limits.
If time permits, begin implementing contact photo upload feature.

3. Any blockers or challenges?

Still experiencing occasional silent failures with no logs—might require deeper Spring Boot debugging or enabling trace-level logging.




