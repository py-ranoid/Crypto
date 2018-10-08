## Common Cryptography algorithms (implemented in core Java)

### Ex1 : Substitution Ciphers
- [Caesar Cipher](https://github.com/py-ranoid/CryptoLab/blob/master/Ex1/CaesarCipher.java)
    ```
    Enter the message (lower Case): 
    hello world
    Enter the key value (displacement): 
    5
    Encrypted : mjqqt btwqi
    Decrypted : hello world

    Enter the encrypted string : 
    mjqqt btwqi
    Key Matched :5
    DECRYPTED :hello world
    ```

- [Playfair Cipher](https://github.com/py-ranoid/CryptoLab/blob/master/Ex1/PlayFair.java)
    ```
    Enter key: 
    monarchy
    M O N A R 
    C H Y B D 
    E F G I K 
    L P Q S T 
    U V W X Z 
    Enter message to encrypt: 
    hello
    Encrypted : CFSUAV
    Decrypted : HELXOX
    ```

### Ex3 : Transposition Ciphers
- [Rail Fence Cipher](https://github.com/py-ranoid/CryptoLab/blob/master/Ex3/RailFence.java)
    ```
    Enter the key : 
    4

    Enter the message : 
    defend the east wall of the castle
    RailFence Matrix :
    d***** *****a*****l*****t*****s***
    *e***d*t***e*s***a*l*** *h***a*t**
    **f*n***h* ***t*w*** *f***e*c***l*
    ***e*****e***** *****o***** *****e

    Encrypted :
    d altsedtesal hatfnh tw feclee o e

    Decrypted :
    defend the east wall of the castle
    ```
- [Columnar Transposition Cipher](https://github.com/py-ranoid/CryptoLab/blob/master/Ex3/RowColCipher.java)
    ```
    Enter the message: 
    hello world
    Enter the number of columns: 
    4
    Enter key: 
    4
    2
    3
    1
    Encrypted : lrxewdloxhol
    Decrypted : helloworldxx
    ```