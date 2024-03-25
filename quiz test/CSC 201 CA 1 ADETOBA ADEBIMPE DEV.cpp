#include <iostream>

using namespace std;
string input;
int score = 0;
int i;
int j;
int total_score;
string user_input;
string questions[10]= {"1. How many planets are there in the solar system?\nA. 9\nB. 2\nC. 3\nD. 10",
                       "2. Which of the following is an input device?\nA. Printer\nB. Mouse\nC. Speaker\nD. Television",
                       "3. Nigeria has how many states?\nA. 22\nB. 26 \nC. 37\nD. 36",
                       "4. Where is Nigeria?\nA. Asia\nB. West \nC. Antartica \nD. Africa",
                       "5. Lions are from the family of?\nA. Cats\nB. Dogs \nC. Antelopes\nD. Snakes",
                       "6. Which country is in Asia\nA. Minneapolis\nB. Senegal \nC. China\nD. Britain",
                       "7. Which colour is not in the rainbow\nA. Red\nB. Black \nC. Blue\nD. Violet",
                       "8. Earth is the ___ planet? \nA. 10th\nB. 5th \nC. 3rd\nD. 9th",
                       "9. Which of the following states are in the North in Nigeria\nA. Osun\nB. Kaduna \nC. Lagos\nD. Imo",
                       "10. Which is an electronic device?\nA. Newspaper\nB. Letter \nC. Drum\nD. Laptop",
                      };
string capital_answers[10]= {"A","B","D","D","A","C","B","C","B","D"};
string answers[10]= {"a","b","d","d","a","c","b","c","b","d"};
string picked_answers[10];

bool firstchecker(string input)
{
    if(input == "1"||input == "2")
    {
        return true;
    }
    else
    {
        return false;
    }
}
bool secondchecker(string user_input)
{

    if(user_input == "A" || user_input == "B" || user_input == "C" || user_input == "D")
    {
        return true;
    }
    else if(user_input == "a" || user_input == "b" || user_input == "c" || user_input == "d")
    {
        return true;
    }
    else if(user_input == "P" || user_input == "p" )
    {
        return true;
    }
    else if(user_input == "N" || user_input == "n" )
    {
        return true;
    }
    else if(user_input == "2" )
    {
        return true;
    }
    else if(user_input == "S" || user_input == "s" )
    {
        return true;
    }
    else
    {
        return false;
    }
}

int main()
{
    cout << "Welcome to my quiz " << endl;
    cout << "------------------\n " << endl;
    cout<<"Press 1 to Start"<<endl;
    cout<<"Press 2 to Exit"<<endl;
    getline(cin,input);
    firstchecker(input);

    while(firstchecker(input)==false)
    {
        cout << "Invalid answer. Please try again \n "<< endl;
        cout<<"Press 1 to Start"<<endl;
        cout<<"Press 2 to Exit"<<endl;
        getline(cin,input);
        firstchecker(input);
    }


    while(firstchecker(input)==true)
    {
        if (input == "1")
        {
            cout << "Rules of the game" << endl;
            cout << "------------------" << endl;
            cout << "There are 10 questions" << endl;
            cout << "To answer a question type in an appropriate answer and then click enter" << endl;
            cout << "You can skip a question and return to it" << endl;
            cout<<  "To skip a question press 'N'" << endl;
            cout << "To move to the previous question press 'P'" << endl;
            cout << "To submit press 'S' " << endl;
            cout << "Enter '2' to exit quiz \n" << endl;
            break;
        }
        else if(input == "2")
        {
            cout << "You have exited the quiz"<<endl;
            exit(0);
        }
    }
    for (i = 0; i<10; i++)
    {
        cout <<  questions[i] << endl;
        cout << "Enter answer: " << endl;
        getline(cin,user_input);

        while(secondchecker(user_input)==false)
        {
            cout << "Invalid input. Please try again: ";
            getline(cin,user_input);
        }

        if(user_input == capital_answers[i] || user_input == answers[i])
        {
            cout << endl;
            picked_answers[i] = user_input;

        }

        else if(user_input == "P" || user_input == "p" && i != 0)
        {
            cout << " You have moved to the previous question" << endl;
            picked_answers[i] = "";
            i = i - 2;

        }
        else if((user_input == "P" || user_input == "p") && (i == 0))
		{
            cout << "You cannot move to a previous question\n";
            i = --i;
        }

        else if((user_input == "N"||user_input == "n") && (i != 9))
        {
            cout << "You have moved to the next question"  <<endl;
            cout << endl;
            picked_answers[i] = "" ;

        }
        else if(user_input == "N"||user_input == "n" && i == 9){
            cout << "This is the last question, you cannot move to the next question\n";
            i = --i;
        }

        else if (user_input == "2")
        {
            cout << "You have exited the quiz" <<endl;
            exit(0);
        }
        else if (user_input == "S" || user_input == "s")
        {
            picked_answers[i] = "" ;
            break;
        }

        else if(user_input != capital_answers[i] || user_input != answers[i])
        {
            cout << endl;
            picked_answers[i] = user_input;

        }
    }
    for(i=0;i<10;i++)
    {
        if(picked_answers[i]==capital_answers[i] || picked_answers[i] == answers[i]){
            total_score = ++score;
        }
        else
        {
            total_score = score;
        }
    }


    cout << "Score: " << total_score<< "/10 "<<endl;
    cout << endl;
    cout <<"Do you want to view your script? Yes/No"<<endl;
    getline(cin,user_input);

    for(j = 0; j < 10; j++)
    {
        if(user_input == "NO" ||user_input == "No"||user_input == "no")
        {
            break;
        }
        else if (user_input == "yes" ||user_input == "Yes"||user_input == "YES")
        {
            cout << "-----------" << endl;
            cout << endl;
            cout << questions[j] <<endl;
            cout << "\nYour option: "<< picked_answers[j] << endl;
            cout << "\nCorrect answer: "<< capital_answers[j] << "/"<< answers[j] << endl;
        }
    }
}



















