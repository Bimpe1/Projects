#include <iostream>
using namespace std;
int main() {
	float num1,num2, result;
	string ans;
	cout<<"My Calculator\n\n";
	cout<<"Input the first number: ";
	cin>>num1;
	cout<<"Input the second number: ";
	cin>>num2;
	cout<<"What operation do u want to perform?\n";
	cout<<"a. Addition    b. Subtraction     c. Multiplication     d. Division"<<endl;
	cin>>ans;
	
	if(ans =="a"||ans=="A"){
		result = num1+num2;
		cout<<"The answer is: "<<result<<endl;
	}
	else if(ans =="b"||ans=="B"){
		result = num1-num2;	
		cout<<"The answer is: "<<result<<endl;
	}
	else if(ans=="c"||ans=="C"){
		result = num1*num2;	
		cout<<"The answer is: "<<result<<endl;
	}
		else if(ans=="d"||ans=="D"){
		result = num1/num2;
		cout<<"The answer is: "<<result<<endl;
	}
	else{
		cout<<"The number you entered is not valid."<<endl;
	}
	return 0;
}