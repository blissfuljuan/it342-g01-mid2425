[Date: 06-09-2025]
1. What did I accomplish yesterday?
I learned the basics of Git, including version control concepts like commits, branches, merges, and remotes. I practiced initializing repositories, staging and committing files, creating branches, and pushing to remote repositories. I also explored the importance of branching and how to use git checkout, git merge, and git rebase to manage branches effectively. Additionally, I learned to use .gitignore to exclude specific files or directories from version control.

2. What will I do today?
I will explore Git merge conflict resolution strategies and tools. I plan to review the differences between git merge and git rebase in collaborative projects. I will also practice removing sensitive files from Git history if committed by mistake.

3. Any blockers or challenges?
I am unsure how to effectively resolve complex merge conflicts involving multiple overlapping changes. I am confused about when to use git merge versus git rebase in team settings. I also need to understand how to safely remove accidentally committed sensitive files from Git history.

[Date: 06-09-2025]
1. What did I accomplish yesterday?
I learned about the importance of branching in Git and how branches allow me to work on features independently without affecting the main codebase. I practiced using commands like git checkout, git merge, and git rebase to manage branches effectively and maintain a clean commit history.

2. What will I do today?
I will focus on understanding the practical differences between merging and rebasing. I plan to study scenarios where each is appropriate and try using both approaches in a team setting.

3. Any blockers or challenges?
I am still unsure about the practical difference between git merge and git rebase, and I need clarity on when to prefer one over the other in a collaborative project.

[Date: 06-09-2025]
1. What did I accomplish yesterday?
I learned how to use .gitignore files to prevent certain files or directories from being tracked by Git. This is especially useful for excluding sensitive data or unnecessary build artifacts from the repository to keep the version history clean and secure.

2. What will I do today?
I will research methods to remove sensitive files that were committed before being added to .gitignore. I plan to try tools like git filter-branch or git rebase to rewrite history safely.

3. Any blockers or challenges?
I’m not sure what the best method is for completely removing sensitive files from Git history after they have already been committed. I want to ensure it’s done thoroughly and correctly.

[Date: 06-16-2025]
1. What did I accomplish yesterday?
I worked on integrating Google Contacts into my Spring Boot application. I successfully set up the Google API Console, configured OAuth2 credentials, and obtained access tokens to authenticate users. I was able to retrieve basic contact information using the Google People API.

2. What will I do today?
I will continue refining the integration by implementing features to add and update contact details. I also plan to handle token refresh logic to maintain seamless user authentication without requiring repeated logins.

3. Any blockers or challenges?
Understanding the structure of the Google People API response takes time, especially when dealing with nested fields like names, phone numbers, and email addresses. I also need to ensure proper handling of OAuth scopes to avoid permission issues.

[Date: 06-13-2025]
1. What did I accomplish yesterday?
I learned how to integrate OAuth2 authentication into my Spring Boot application. I was able to configure the necessary dependencies, set up client credentials, and implement a basic OAuth2 login flow. This allowed my application to securely authenticate users through external providers.

2. What will I do today?
I will explore using OAuth2 to access third-party APIs that require user authorization, starting with setting up integration with Google services such as Google Contacts.

3. Any blockers or challenges?
Understanding the OAuth2 token lifecycle and how to handle token refresh securely is still a bit confusing. I also want to make sure I’m following best practices for storing and securing client secrets.

[Date: 06-20-2025]
1. What did I accomplish yesterday?
I continued working on the Google Contacts integration in my Spring Boot application. I successfully implemented functionality to add new contacts and update existing ones using the Google People API. I also handled token refresh logic to keep user sessions active without manual re-authentication.

2. What will I do today?
I will focus on improving the user interface for managing contacts and ensuring that error handling is in place for common API issues. I plan to test the application thoroughly with different Google accounts to confirm stability and correct scope usage.

3. Any blockers or challenges?
There are still some inconsistencies in how the People API returns contact fields across different accounts, which makes parsing data a bit tricky. I also need to ensure that all scopes are properly requested and granted during the OAuth flow.

