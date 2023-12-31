# stonks - CSC207 course project

## Installation Instructions

1. Clone the repository using any CLI with git installed using "git clone https://github.com/awesominat/stonks.git" (alternatively, you can download the repo as a zip folder)
2. Install Maven using the instructions found [here](https://maven.apache.org/install.html). Alternatively, if you are on MacOS and have brew installed, you can install maven with `brew install maven`
3. Navigate to the project directory in the terminal. Run `mvn clean install` in the project directory.
4. Create a free account on [Finnhub](finnhub.io) and copy your API key.
5. Create a file named `file.txt` in the root directory of the project and paste the API key (make sure that there are no leading/trailing spaces, the file only contains your API key).
6. Navigate to src/app and run `Main.java`

## General Description
The broad purpose of the software is to give users a platform to invest in stocks, view their profits, view stock price 
trends, and copy other users portfolio investments, all using fake money to remove the risk aspect.

Our software attempts to solve the problem investors have when they want to practice investing risk-free.

## User Stories

User Story 1:
As a stock investor ready to start my investment journey, I would like to buy any number of a specific stock and have 
the stocks I’ve bought saved so I don’t lose my investments. This feature will allow me to test my theories in stock 
investments so I can be better prepared when I spend my real money.

User Story 2:
As an investor who has lost all my money, I would like to reset my account so that I can start over again since the 
imaginary investments I have made through the Stonks™ app did not go as I had hoped, and I now wish to simulate other 
investment strategies.

User Story 3:
As an investor interested in buying a stock, I would like to view comprehensive information about that specific stock, 
such as their previous prices, current prices, and percentage change in the last day, so I can make an informed decision 
about how to spend my money.

User Story 4:
As a finance enthusiast worried about whether I made the right choice buying a stock, I would like an intuitive overview 
of all the stocks I am currently invested in and how they are performing. As in, I would like to see the percentage 
profit or loss for every stock I have purchased.

User Story 5:
As an aspiring investor who understands the stock market is changing rapidly, I would like to conveniently sell stock 
using the fake money in my portfolio and know how much I have profited/lost from what stocks (of what company) as well 
as how much money my account will have after I sell. These features are so that I can effectively track how well I am 
progressing in my investment journey.

# Screens and design
![Screens](stonks-screens.png)
