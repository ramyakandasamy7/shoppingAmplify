# shoppingAmplify

## How to Run this Code

1. In order to run this project, git clone this repository on your computer.
2. Install amplify CLI with sudo npm install -g @aws-amplify/cli
3. Pull the correct amplify backend  amplify pull --appId d1qmius55ek1j5 --envName rebuild
4. There is a bug within Amplify that may require you to remove 'apply plugin: 'com.amplifyframework.amplifytools' in the project settings, rebuild, and then add the plugin back in


## Distribution of Work

Ramya - I created Amplify backend environmen and connected the environment to basic front-end UI. I also added scanning functionality for store and the scanning functionality for products

Raymond - Raymond added user authentication, the ability to view previously selected store, and shopping cart functionality using shared preferences

Jed - Jed did much of the UI design 
