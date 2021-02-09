# Experiment 1: Learning Encryption for Java 
For this experiment, I wanted to learn how we are going to encrypt our data for 
the messaging application. To do this, I set up a simple static utility library
that I called EncryptionSuite that contain some much easier to use methods to 
get encryption keys and en/decrypt text. It does use any external libraries so I 
assume it is portable to android. 

### What I learned from this 
- How to utilize Java's libraries to use RSA and AES to encrypt data securely
- We will need to encrypt our data in AES
- RSA public/private keys are used to en/decrypt the AES secret key
  - RSA can only encrypt so many bits
- Springboot sends the returned text of a Get-Mapped function to the client 
    that connects to it. This means my browser interprets it as html.
  - I could be interpretting this incorrectly 