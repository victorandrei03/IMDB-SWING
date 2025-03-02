## Application Overview

Upon launching the application, the user chooses how they want to use it by entering `1` for terminal mode or `2` for the Swing GUI.

---

## Functionalities

### 1. Login & View Productions/Actors in the System

#### Terminal Mode:
- Implemented with an infinite `while` loop where the user must enter their email and password until they successfully log in or choose to exit the application by pressing `2`.
- Upon successful login, the user is presented with a menu of operations they can perform based on their user type (Admin, Regular, Contributor).
- Options `1` and `2` allow the user to view all productions and actors, regardless of their user type.

#### Swing Mode:
- Used a `JTextField` for email, a `JPasswordField` with a button to show/hide the password, and a "Login" button to verify the user's credentials.
- If the credentials are correct, the user is taken to the menu panel (using a `CardLayout` and a parent panel) where they can see two lists (`JScrollPane`) containing images of the most appreciated productions and some favorites from other users. Clicking on these images takes the user to the respective production's page.
- The menu panel also includes:
  - A logo button (present in almost all panels) that returns the user to the menu.
  - A menu button with functionalities specific to the current user type (e.g., "Remove User" for Admins).
  - A search bar that responds to the "Enter" key or a "Search" button, displaying all productions/actors that match the entered string (e.g., "Ba" will show results starting with "Ba"). If no characters are entered, all productions/actors in the system are displayed.
  - The user's experience points are displayed if their type is not "Admin".
- A "Register" button on the login page allows new users to register by entering their details (name, username, email, password, age, etc.).

![image](https://github.com/user-attachments/assets/6279f411-0019-4f79-b978-82911b659afa)

![image](https://github.com/user-attachments/assets/cd538363-5fc8-473b-9080-cd3cb1c848c6)

---

### 2. View Notifications

- Implemented using the **Observer** design pattern, where the `User` class implements the `Observer` interface, and the `Rating` and `Request` classes act as `Subject`.
- Notifications are sent when:
  - A Regular user rates a production added by another user, and if the production is in multiple users' favorites, they will also receive notifications.
  - A request is created or deleted, and the Admin/Contributor is notified based on the request type.
  - The user who created the request is notified if it is resolved or rejected.

#### Terminal Mode:
- The user can choose to view notifications one by one or delete them.

#### Swing Mode:
- A `JButton` and `JPopupMenu` are used to display all received notifications when the button is clicked.

- ![image](https://github.com/user-attachments/assets/10e1dad7-db2a-4688-9d14-dc97a6b1aceb)

---

### 3. Search for a Specific Movie/Series/Actor

#### Terminal Mode:
- The user must enter the full name of the production/actor, and all relevant information is displayed.

#### Swing Mode:
- The search bar and button (explained in step 1) are used. Clicking on an image takes the user to the respective page.

![image](https://github.com/user-attachments/assets/68290b54-7a12-40a3-ab22-ceaa5c03b5a9)

![image](https://github.com/user-attachments/assets/3a29eece-e8b9-45e2-9bd9-7a824bb3c1d4)

![image](https://github.com/user-attachments/assets/b7106da5-7bdb-46b9-8164-284ec89779d9)

---

### 4. Add/Remove a Production/Actor to/from Favorites

#### Terminal Mode:
- The user selects the type (Actor/Production) and then enters the name of the item to add/remove.

#### Swing Mode:
- A `JComboBox` displays the user's favorite productions/actors. Selecting an item takes the user to its respective page.
- To add/remove an item, the user enters the name and clicks the "Add" or "Remove" button.

---

### 5. Create/Delete a Request

#### Terminal Mode:
- To create a request, the user must enter the recipient's username, request type, problem description, and the production/actor name.
- To delete a request, the user selects the request by its index.

#### Swing Mode:
- A `JComboBox` allows the user to select "Create Request" or "Remove Request".
  - For "Create Request", a new `JComboBox` appears to select the request type. Depending on the type, the user enters the required information.
  - For "Remove Request", a `JComboBox` displays the requests the user has created, and the user can delete the selected request.

---

### 6. Add/Remove a Production from the System

#### Terminal Mode:
- To delete, the user enters the name of the production/actor they want to remove (must have been added by them).
- To add, the user enters the production/actor's name, biography/plot, and creates lists of actors/directors, seasons, and episodes.

#### Swing Mode:
- Two separate panels are created for adding/removing productions. The removal panel contains a `JComboBox` with the names of contributions and a "Remove" button. The addition panel requires the user to fill in all fields and upload an image.

---

### 7. View/Resolve Sent Requests

#### Terminal Mode:
- Contributors can view their list of requests, while Admins can also view the shared admin request list.
- To resolve a request, the user selects it by its index and marks it as resolved.

#### Swing Mode:
- Two `JComboBox`es allow the user to select the request list and the user who made the request. The user can then resolve or reject the request.

---

### 8. Update Information About Productions/Actors

#### Terminal Mode:
- The user selects the operation by its index (e.g., `1` to modify the title/name) and enters the new information.

#### Swing Mode:
- A `JComboBox` displays the current contributions, and the user can modify them by selecting the operation and entering the new information.

---

### 9. Add/Remove a Review for a Production

#### Terminal Mode:
- The user can add a review if they haven't already rated the production. They enter the rating and comment, and experience points are awarded using the **Strategy** pattern.
- To remove a review, the system checks if the user has already added one and removes it if necessary.

#### Swing Mode:
- The "Add Rating" button is placed at the end of the production page. If the user has already added a rating, the button changes to "Remove Rating".

---

### 10. Add/Remove a User

#### Terminal Mode:
- The user enters all necessary information to add a new user, ensuring the username/email doesn't already exist.
- To delete, the user enters the username of the user they want to remove.

#### Swing Mode:
- `JTextField`s and `JComboBox`es are used for adding users. For deletion, a `JComboBox` lists all users, and the selected user is removed.

---

![image](https://github.com/user-attachments/assets/7990db7b-99f4-4b50-a5a8-cd312a5bd5b5)

![image](https://github.com/user-attachments/assets/95293d83-80c3-41f2-83ca-0cd77ff9ec30)

