# Experiment 2: Hashing passwords for Authentication 
For this experiment, I wanted find a library that we could use for hashing 
passwords so that we will store them securely. I found Bcrypt which uses
OpenBSD's Blowfish to hash passwords. I wrote a simple utility library and
changed the people to users containing their hashed password.
I also created a simple demo of hashing and verifying password.
I wanted to have a user creation page, but I could find any examples of how 
to do that without using Springboots premade login system. 

### What I learned from this 
- How we can store passwords in our database
- Springboot already has a Security system that handles login
  - I don't know how to uses it nor if it works with Android
  - We will probably want the security system but may not want it 
    handling logins for us
- Working a little bit with managing the integrated database that 
  the hellopeople tutorial showed
  - We will be using one on a remote server so 
    I'm not sure how much this is useful
 