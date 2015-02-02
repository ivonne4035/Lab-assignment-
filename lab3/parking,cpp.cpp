#include <iostream>
#include <sstream>
#include <string>
#include "parking.h"

using namespace std;

class parking
{
	double hours;
	double total;
public:
	parking(int);
	void findTotal();
};

parking::parking(int data)
{
	total = 2;
	hours = data - 1;
}

void parking::findTotal(void)
{
	while (hours > 0)
	{
		total += 1.2;
		hours--;
	}
	cout << "You owe: $" << total << endl;
}

int main()
{
		string entered;
		int length;

		cout << "How many hours were you parked: ";
		cin >> entered;
		stringstream(entered) >> length;

		parking total(length);
		total.findTotal();

		return 0;	
}


