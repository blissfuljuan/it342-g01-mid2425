"# Git Journal Entries" 
## june 8, 2025
**What I learned in Git:**  
How to initialize a repository, stage files, and make commits.

**Confusion:**  
What exactly does `HEAD` refer to in Git?

## june 8, 2025
**What I learned in Git:**  
How branching works and how to switch branches.

**Confusion:**  
When to use `rebase` vs `merge`?

## June 8, 2025
**What I learned in Git:**  
How to push changes to a remote repository.

**Confusion:**  
What if someone else pushes a conflicting change before I do?

## June 16, 2025
**What I learned in Git:**  
Today, I learned the fundamental Git commands such as git init, git add, git commit, and git push. I now understand how to initialize a repository, stage changes, commit them with a message, and finally push those commits to a remote repository like GitHub. I also learned how Git tracks the history of changes, which helps in managing different versions of a project..

**Confusion:**  
I'm still a bit confused about the purpose of the staging area—why we need to git add before committing. It’s also unclear how to properly remove a file that was mistakenly added to the staging area or committed. Do I use git rm, git reset, or something else?

## June 20,2025
**1. What did I accomplish yesterday?**
- Reviewed untracked files in my Git repository.
- Resolved the issue with adding files to Git and initialized a submodule.
- Worked on setting up the Agile Daily Stand-Up format for the journal.

**2. What will I do today?**
- Update my GitHub repository with the daily stand-up format.
- Address any additional Git issues (if any come up).
- Begin reviewing and planning for upcoming tasks in my project.

**3. Any blockers or challenges?**
- None at the moment, but I need to make sure my Git submodule is fully initialized.
- Need to ensure consistent updates to this journal moving forward.

# 📝 Phase 1: Google Contacts Integration - Journal Log
**Project:** googlecontacts  
**Author:** Ross Mikhail Vestil  
**Phase:** Define Functional and Non-Functional Requirements  
**Dates:** June 23–25, 2025

---

## [Date: 06-23-2025]

**1. What did I accomplish yesterday?**
- Set up Google Cloud project for OAuth 2.0
- Enabled People API
- Created OAuth credentials and test user

**2. What will I do today?**
- Configure `application.properties` for OAuth
- Begin implementing Google login
- Verify redirect and login flow

**3. Any blockers or challenges?**
- Initial confusion on redirect URI format
- Google test user consent screen pending

---

## [Date: 06-24-2025]

**1. What did I accomplish yesterday?**
- Completed Google login flow using Spring Security
- Verified access token and fetched basic user info
- Started controller to fetch Google Contacts

**2. What will I do today?**
- Parse and display contact names/emails using Thymeleaf
- Handle missing contact fields gracefully
- Draft functional and non-functional requirements

**3. Any blockers or challenges?**
- JSON format from Google API was verbose
- Needed fallback for null/empty contact data

---

## [Date: 06-25-2025]

**1. What did I accomplish yesterday?**
- Displayed Google Contacts in a list view
- Implemented fallback UI for missing values
- Created outline of SRS and user stories

**2. What will I do today?**
- Finalize and write full SRS file
- Create use case diagram (Login & View Contacts)
- Optional: draw UI sketch of homepage & contact list

**3. Any blockers or challenges?**
- 500 Internal Server Error from malformed `contacts.html` (resolved)

---

# ✅ Phase 1 Summary: Requirements

## 🔧 Functional Requirements:
- User can log in using Google (OAuth 2.0)
- App retrieves user’s Google Contacts
- Contacts are displayed in UI (name + email)
- Contacts list is only visible after login

## 🔒 Non-Functional Requirements:
- Secure access token management
- Quick response time (<2 seconds after login)
- Compatibility with Chrome, Firefox, Edge
- Error handling for null/missing contact data

## 📄 Deliverables:
- ✅ Software Requirements Specification (SRS)
- ✅ Use Case Diagram (Login, View Contacts)
- ✅ User Stories:
    - “As a user, I want to log in with Google so I can securely access my contact list.”
    - “As a user, I want a clear list of my contacts’ names and emails.”

