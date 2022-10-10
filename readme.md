**Problem statement** : Password Validation

We like to build code for purposes of password verification. Verification will fail if any one
of the rules mentioned does not pass.
1. Implement the following rules. Each one of these will throw an exception with a
   different message of your choice
   - password should be larger than 8 chars
   - password should not be null
   - password should have one uppercase letter at least
   - password should have one lowercase letter at least
   - password should have one number at least
2. Add feature: Password is OK if at least three of the previous conditions is true
3. Add feature: password is never OK if item 1.d is not true.
4. Assume Each verification takes 1 second to complete. How would you solve items 2
   and 3 so tests can run faster?

**Assumptions** :
 - Special characters are not in scope of password verification
 - For 'password should be larger than 8 char' should have max 255 characters
 - Code should be covered more than 80 % 
 - Allowed to use regex to do password validation
 - Allowed to use Junit libraries for unit testing