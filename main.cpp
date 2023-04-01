#include <iostream>
#include<exception>
#include<new>
using namespace std;
//float num, ans, denom;


/*void func(inttest)  throw(int, char, double){
if (test==0)   throw test;
if (test==1)   throw 'a';
if (test==2)   throw 123.23;
}*/


int main()
{
 /*  cout<<"Handling Exceptions"<<endl;
   cout<<"Enter Numerator: "<<endl;
   cin>>num;
   cout<<"Enter Denominator: "<<endl;
   cin>>denom;
   try{
    if (denom==0)
        throw denom;
        ans=num/denom;
        cout<<"Output: "<<ans;
      }
      catch(float e){
         cout<<"The denominator is zero";
      }


 try{
 func(1);
 }
    catch(int i){
    cout<<"Caught int\n";
    }
    catch(char c){
    cout<<"Caught char\n";
    }
    catch(double d){
    cout<<"Caught double\n";
    }*/


   try{
   int *myarray = new int[1000000000000];
   }
    catch(bad_alloc &exception){
    cerr<<"bad_alloc detected: "<<exception.what();
    }
    return 0;
}
