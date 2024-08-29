# Bloom

CHIP-8 interpreter/emulator/virtual machine.

## Description

Created in 1977, CHIP-8 is the original fantasy console. Initially designed to ease game development
for the COSMAC VIP kit computer, it has enjoyed several revivals over the decades for new and
exciting platforms. Today, creating a CHIP-8 implementation is a rite of passage for anyone
interested in learning about emulation.

## Used resources

* [Guide to making a CHIP-8 emulator](https://tobiasvl.github.io/blog/write-a-chip-8-emulator)
* [Cowgod's Chip-8 Technical Reference v1.0](http://devernay.free.fr/hacks/chip8/C8TECH10.HTM)
* [A collection of ROM images with tests](https://github.com/Timendus/chip8-test-suite)
* [CHIP-8 Archive](https://johnearnest.github.io/chip8Archive/)

## How to use

1. Package the code into a JAR:
    ```shell
   mvn package
   ```
2. Execute the CHIP-8 program using the packaged emulator:
    ```shell
   java -jar ./target/bloom-1.0-SNAPSHOT.jar <path or link to your program>
   ```
