#include <iostream>
#include <fstream>
#include <string>
#include <regex>
#include <sstream>
#include <iomanip>
#include <ctime>
#define size 36
using namespace std;
//declaring important parameters
ifstream myFile;
ifstream adFile;
fstream mFile;
ofstream meFile;
stringstream stream;
int lineCount, j = 0;
float cumm_gpa = 0;
bool boo = false;
string choice, password, Student_Name, Admin_Name, Admin_Pass, Matric_No, CSC201, CSC205, MTH201, MTH205 , GST201, GPA, name;
string student_name[size],admin_name[9], admin_pass[9], matric_no[size], csc201[size], csc205[size], mth201[size], mth205[size], gst201[size], gpa[size];


//convert string to uppercase
string upper(string var){
	getline(cin, var);
	for(int i = 0; i < var.length(); i++){
		var[i] = toupper(var[i]);
	}
	return var;
}
//checks if string is an int in range of 0-100
string valChange(int x){
	string var;
	cout<<"Enter value: ";
	getline(cin, var);
	if(regex_match(var, regex("^[0-9]{1,3}$"))){
		int val = stoi(var);
		if(val>100){
				cout<<"Invalid input"<<endl;
				return valChange(x);
			}else{
				cumm_gpa += x * ((val > 44)?(val > 49)?(val > 59)?(val > 69)?5:4:3:2:0);
				return var;
			}
	}else{
		cout<<"Invalid input"<<endl;
		return valChange(x);
	}
}
//gets index and prints details for specific mat no
void showValue(){
	j = 0;
	boo=false;
	cout<<"Enter student's matriculation number: ";
	choice = upper(choice);
    	for(string i: matric_no){
    	if(i == choice){
    		boo = true;
    		break;
		}else{
			j++;
		}
	};
	if(boo){
		cout<<"Student Name: "<<student_name[j]<<"\nMatric No: "<<matric_no[j]<<"\nCSC201: "<<csc201[j]<<"\nCSC205: "<<csc205[j]<<"\nMTH201: "<<mth201[j]<<"\nMTH205: "<<mth205[j]<<"\nGST201: "<<gst201[j]<<"\nGPA: "<<gpa[j]<<"\n\n";
	}else{
		cout<<"Invalid matric number"<<endl;
		showValue();
	};
	}

int main(){
	adFile.open("admins.txt");
	if(adFile.is_open()){
		lineCount = 0;
		while(!adFile.eof()){
			getline(adFile, Admin_Name, ',');
			admin_name[lineCount] = Admin_Name;
			getline(adFile,Admin_Pass, '\n');
			admin_pass[lineCount] = Admin_Pass;
			lineCount++;
		}
	}else{
		cout<<"File is non-existent"<<endl;
	}
	adFile.close();
	cout<<"\t\tPan-Atlantic University\n";
	cout<<"\t\tResult Management System\n";
	Login:
		cout<<"\nProvide your Login Details\n";
		cout<<"Username: ";
		getline(cin, choice);
		cout<<"Password: ";
		getline(cin, password);
		j = 0;
		lineCount = 0;
		boo = false;
		for(auto i : admin_name){
			if(i == choice){
				boo = true;
				break;
			}else{
				j++;
			}
		}
		if(boo){
			//checks username and password
			if(password == admin_pass[j]){
				choice[0] = toupper(choice[0]);
				time_t now = time(0);
				string dt = ctime(&now);
				cout<<"\nWelcome "<<choice<<",\t\t\t\t\tLogin time: "<<dt<<endl;
				lineCount = 0;
				myFile.open("studentResult.txt");
				if(myFile.is_open()){
					while(!myFile.eof()){
						getline(myFile, Student_Name, ',');
						student_name[lineCount] = Student_Name;
						getline(myFile, Matric_No, ',');
						matric_no[lineCount] = Matric_No;
						getline(myFile, CSC201, ',');
						csc201[lineCount] = CSC201;
						getline(myFile, CSC205, ',');
						csc205[lineCount] = CSC205;
						getline(myFile, MTH201, ',');
						mth201[lineCount] = MTH201;
						getline(myFile, MTH205, ',');
						mth205[lineCount] = MTH205;
						getline(myFile, GST201, ',');
						gst201[lineCount] = GST201;
						getline(myFile, GPA, '\n');
						gpa[lineCount] = GPA;
						lineCount++;
					}
				}else{
					cout<<"File is non-existent"<<endl;
				}
				myFile.close();
				mainMenu:
					cout<<"\n\t\tMAIN MENU"<<endl;
					cout<<"To SEARCH for a student's result --- press F"<<endl;
					cout<<"To UPDATE a student's result --- press U"<<endl;
					cout<<"To SAVE a student's result to file --- press P"<<endl;
					cout<<"To VIEW all results --- press A"<<endl;
					cout<<"To EXIT the program or GO BACK to main menu --- press E"<<endl;
					cout<<"Enter an option: ";
					choice = upper(choice);
					if(choice.length()>1){
						cout<<"Invalid input\n";
						goto mainMenu;
					}else if ( choice =="E"||"e"){
                        exit(0);
				}


                    else{

						switch (choice[0]){

							default:
								cout<<"Invalid input\n";
								goto mainMenu;
								meFile.close();
								goto mainMenu;
								break;
							case 'F':
								cout<<"\nSEARCH RECORDS"<<endl;
								showValue();
								goto mainMenu;
								break;
							case 'U':
								cumm_gpa = 0;
								cout<<"\nRECORD UPDATE"<<endl;
								showValue();
								cout<<"\nCSC201 Update"<<endl;
								csc201[j] = valChange(3);
								cout<<"\nCSC205 Update"<<endl;
								csc205[j] = valChange(3);
								cout<<"\nMTH201 Update"<<endl;
								mth201[j] = valChange(3);
								cout<<"\nMTH205 Update"<<endl;
								mth205[j] = valChange(3);
								cout<<"\nGST201 Update"<<endl;
								gst201[j] = valChange(2);
								stream.str("");
								stream.precision(2);
								stream<<fixed;
								stream<<(cumm_gpa/14);
								gpa[j] = stream.str();
								meFile.open("studentResult.txt");
								if(meFile.is_open()){
									for(int i = 0; i < size; i++){
										meFile<<student_name[i]<<","<<matric_no[i]<<","<<csc201[i]<<","<<csc205[i]<<","<<mth201[i]<<","<<mth205[i]<<","<<gst201[i]<<","<<gpa[i];
										if(i<size - 1){
											meFile<<endl;
										}
									}
									cout<<"Record written and updated to file"<<endl;
								}else cout<<"Unable to open file";
							case 'P':
								cout<<"\nPRINT RESULT TO FILE"<<endl;
								showValue();
								name = student_name[j]+".txt";
								mFile.open(name, ios::out);
								mFile<<"Student_Name,Matric_No,CSC201,CSC205,MTH201,MTH205,GST201,GPA\n";
								mFile<<student_name[j]<<","<<matric_no[j]<<","<<csc201[j]<<","<<csc205[j]<<","<<mth201[j]<<","<<mth205[j]<<","<<gst201[j]<<","<<gpa[j]<<endl;
								mFile.close();
								cout<<"Record written to file with filename "<<name<<"\n"<<endl;
								goto mainMenu;
								break;
							case 'A':
								for(int j = 0; j < size; j++){
									cout<<setfill('-')<<left<<setw(97)<<"-"<<endl;
									cout<<setfill(' ')<<left<<setw(2)<<j<<"| "<<left<<setw(22)<<student_name[j]<<"| "<<left<<setw(14)<<matric_no[j]<<"| "<<left<<setw(7)<<csc201[j]<<"| "<<left<<setw(7)<<csc205[j]<<"| "<<left<<setw(7)<<mth201[j]<<"| "<<left<<setw(7)<<mth205[j]<<"| "<<left<<setw(7)<<gst201[j]<<"| "<<left<<setw(7)<<gpa[j]<<"| "<<endl;
								}
								goto mainMenu;
								break;

						}
					}


			}else{
				cout<<"Incorrect details"<<endl;
				goto Login;
			}
		}else{
			cout<<"Incorrect details"<<endl;
			goto Login;
		}
}
