ifndef parking_h
#define parking_h

class parking

{

public:

parking();
void set_hour(float);
void calculate_charge();
float get_hour();
float get_charge();

private:
float charge,hour;

};
