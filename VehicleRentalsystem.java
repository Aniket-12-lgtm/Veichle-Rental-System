package optix;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


abstract class Vehicle{
	private String vehicleId;
	private String brand;
	private String model;
	private double basePricePerDay;
	private boolean isAvailable;
	
	
	public Vehicle(String vehicleId,String brand,String model,double basePricePerDay) {
		this.vehicleId=vehicleId;
		this.brand=brand;
		this.model=model;
		this.basePricePerDay=basePricePerDay;
		this.isAvailable=true;
		
	}
	public String getVehicleId() {
		return vehicleId;
	}


	public String getModel() {
		return model;
	}
	public double calculatePrice(int rentalDays) {
		return basePricePerDay*rentalDays;
	}
	public boolean isAvailable() {
		return isAvailable;
	}
	public void rent() {
		isAvailable =false;
	}
	public void returnVehicle() {
		isAvailable=true;
	}
	public String getBrand() {
	
		return brand;
	}
	public abstract String getType() ;
		
}
class Car extends Vehicle {
    public Car(String carId, String brand, String model, double basePricePerDay) {
        super(carId, brand, model, basePricePerDay);
    }
    
    @Override
    public String getType() {
        return "Car";
    }
}

// Bike class inheriting from Vehicle
class Bike extends Vehicle {
    public Bike(String bikeId, String brand, String model, double basePricePerDay) {
        super(bikeId, brand, model, basePricePerDay);
    }
    
    @Override
    public String getType() {
        return "Bike";
    }
}
class Customer{
	private String customerId;
	private String name;
	
	public Customer(String customerId,String name) {
		this.customerId=customerId;
		this.name=name;
		
	}
	public String getCustomerId() {
		return customerId;
	}
	public String getName() {
		return name;
	}
}
class Rental{
	private Vehicle vehicle;
	private Customer customer;
	private int days;
	
	public Rental (Vehicle vehicle,Customer customer,int days) {
		this.customer=customer;
		this.vehicle=vehicle;
		this.days=days;
	}
	public Vehicle getVehicle() {
		return vehicle;
	}
	public Customer getCustomer() {
		return customer;
	}
	public int getDays() {
		return days;
	}
}


public class VehicleRentalsystem {
	private List<Vehicle>vehicles;
	private List<Customer>customers;
	private List<Rental>rentals;
	
	public VehicleRentalsystem() {
		vehicles=new ArrayList<>();
		customers=new ArrayList<>();
		rentals=new ArrayList<>();
	}
	public void addVehicle(Vehicle vehicle) {
	
	  vehicles.add(vehicle);
	}
	public void addCustomer(Customer customer) {
		customers.add(customer);
		
	}
	public void rentVehicle(Vehicle vehicle,Customer customer ,int days ) {
		if(vehicle.isAvailable()) {
			vehicle.rent();
			rentals.add(new Rental(vehicle,customer,days));
		}else {
			System.out.println("Vehicle is not avaliable for rent");
		}
	}
	public void returnVehicle(Vehicle vehicle) {
		vehicle.returnVehicle();
		Rental rentalToRemove=null;
		for(Rental rental:rentals) {
			if(rental.getVehicle().getVehicleId().equals(vehicle.getVehicleId())) {
				rentalToRemove=rental;
				break;
			}
		}
		if(rentalToRemove!=null) {
			rentals.remove(rentalToRemove);
				
			}else {
				System.out.print("Car was not rented");
			}
		}
	public void menu() {
		Scanner scanner =new Scanner(System.in);
		
		
		while(true) {
			System.out.println("============= Vehicle Rental System ===========");
			
			System.out.println("1.Rent a vehicle");
			System.out.println("2.Return a vehicle");
			System.out.println("3.Exit");
			int choice=0;
			
			 try {
	                choice = Integer.parseInt(scanner.nextLine());
	            } catch (NumberFormatException e) {
	                System.out.println("Invalid input. Please enter a number.");
	                continue;
	            }
			if(choice==1) {
				System.out.println("\n==Rent a Vehicle==\n");
				System.out.println("1.What do you want to rent?");
				
				System.out.println("Car");
				System.out.println("Bike");
				System.out.println("Enter your choice 1or2 ");
		
				
			int vehicleTypeChoice=0;
			 try {
                 vehicleTypeChoice = Integer.parseInt(scanner.nextLine());
             } catch (NumberFormatException e) {
                 System.out.println("Invalid input. Please enter a number.");
                 continue;
             }
			 String vehicleType=(vehicleTypeChoice==1)?"Car":"Bike";
			 
			 System.out.print("Enter your name");
			 String customerName=scanner.nextLine();
			 System.out.println("\nAvailable"+vehicleType+"s");
			 
			 for(Vehicle vehicle:vehicles) {
				 if(vehicle.isAvailable()&&vehicle.getType().equalsIgnoreCase(vehicleType)) {
					 System.out.println(vehicle.getVehicleId() + " - " + vehicle.getBrand() + " " + vehicle.getModel());
                 }
				 }
			   
             System.out.print("\nEnter the " + vehicleType + " ID you want to rent: ");
             String vehicleId = scanner.nextLine();

             System.out.print("Enter the number of days for rent: ");
             int rentalDays = 0;
             try {
                 rentalDays = Integer.parseInt(scanner.nextLine());
             } catch (NumberFormatException e) {
                 System.out.println("Invalid input. Please enter a valid number of days.");
                 continue;
             }

             Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
             addCustomer(newCustomer);

             Vehicle selectedVehicle = null;
             for (Vehicle vehicle : vehicles) {
                 // Find the vehicle with the correct ID and type
                 if (vehicle.getVehicleId().equals(vehicleId) && vehicle.isAvailable() && vehicle.getType().equalsIgnoreCase(vehicleType)) {
                     selectedVehicle = vehicle;
                     break;
                 }
             }

             if (selectedVehicle != null) {
                 double totalPrice = selectedVehicle.calculatePrice(rentalDays);
                 
                 System.out.println("\n== Rental Information ==\n");
                 System.out.println("Customer ID: " + newCustomer.getCustomerId());
                 System.out.println("Customer Name: " + newCustomer.getName());
                 System.out.println("Vehicle: " + selectedVehicle.getBrand() + " " + selectedVehicle.getModel());
                 System.out.println("Rental Days: " + rentalDays);
                 System.out.printf("Total Price: ₹%.2f%n", totalPrice);

                 System.out.print("\nConfirm rental (Y/N): ");
                 String confirm = scanner.nextLine();

                 if (confirm.equalsIgnoreCase("Y")) {
                     rentVehicle(selectedVehicle, newCustomer, rentalDays);
                     System.out.println("\nVehicle rented successfully!");
                 } else {
                     System.out.println("\nRental canceled.");
                 }
             } else {
                 System.out.println("\nInvalid vehicle selection or vehicle not available for rent.");
             }
         } else if (choice == 2) {
             System.out.println("\n== Return a Vehicle ==\n");
             System.out.print("Enter the vehicle ID you want to return: ");
             String vehicleId = scanner.nextLine();

             Vehicle vehicleToReturn = null;
             for (Rental rental : rentals) {
                 if (rental.getVehicle().getVehicleId().equals(vehicleId)) {
                     vehicleToReturn = rental.getVehicle();
                     break;
                 }
             }

             if (vehicleToReturn != null) {
                 returnVehicle(vehicleToReturn);
             } else {
                 System.out.println("\nInvalid vehicle ID or vehicle is not currently rented.");
             }
         } else if (choice == 3) {
             System.out.println("\nThank you for choosing Vehicle Rental System! 👋");
             scanner.close();
             return;
         } else {
             System.out.println("\nInvalid choice. Please enter a valid option.");
         }
         System.out.println();
     }
 }			 
			
	public static void main(String[] args) {
		VehicleRentalsystem rentalSystem=new VehicleRentalsystem();
		
        rentalSystem.addVehicle(new Car("C001", "Toyota", "Camry", 60.0));
        rentalSystem.addVehicle(new Car("C002", "Honda", "Accord", 70.0));
        rentalSystem.addVehicle(new Car("C003", "Mahindra", "Thar", 150.0));
        rentalSystem.addVehicle(new Car("C004", "Ford", "Mustang", 120.0));
        rentalSystem.addVehicle(new Car("C005", "Chevrolet", "Malibu", 85.0));
        rentalSystem.addVehicle(new Car("C006", "Nissan", "Sentra", 55.0));
        rentalSystem.addVehicle(new Car("C007", "Hyundai", "Elantra", 65.0));
        rentalSystem.addVehicle(new Car("C008", "Kia", "Forte", 60.0));
        rentalSystem.addVehicle(new Car("C009", "Volkswagen", "Jetta", 75.0));
        rentalSystem.addVehicle(new Car("C010", "Subaru", "Impreza", 80.0));
        rentalSystem.addVehicle(new Car("C011", "Mazda", "3", 70.0));
        rentalSystem.addVehicle(new Car("C012", "Tesla", "Model 3", 200.0));
        rentalSystem.addVehicle(new Car("C013", "BMW", "3 Series", 180.0));
        rentalSystem.addVehicle(new Car("C014", "Mercedes-Benz", "C-Class", 190.0));
        rentalSystem.addVehicle(new Car("C015", "Audi", "A4", 175.0));
        rentalSystem.addVehicle(new Car("C016", "Lexus", "ES", 160.0));
        rentalSystem.addVehicle(new Car("C017", "Volvo", "S60", 155.0));
        rentalSystem.addVehicle(new Car("C018", "Jeep", "Wrangler", 130.0));
        rentalSystem.addVehicle(new Car("C019", "Dodge", "Charger", 110.0));
        rentalSystem.addVehicle(new Car("C020", "Chrysler", "300", 95.0));
        rentalSystem.addVehicle(new Bike("B004", "Royal Enfield", "Classic 350", 60.0));
        rentalSystem.addVehicle(new Bike("B005", "Harley-Davidson", "Street 750", 100.0));
        rentalSystem.addVehicle(new Bike("B006", "Suzuki", "Gixxer", 45.0));
        
        
        rentalSystem.addVehicle(new Bike("B001", "Bajaj", "Pulsar", 55.0));
        rentalSystem.addVehicle(new Bike("B002", "Honda", "Unicorn", 40.0));
        rentalSystem.addVehicle(new Bike("B003", "TVS", "Apache", 50.0));
        rentalSystem.addVehicle(new Bike("B004", "Yamaha", "MT-15", 58.0));
        rentalSystem.addVehicle(new Bike("B005", "Royal Enfield", "Classic 350", 65.0));
        rentalSystem.addVehicle(new Bike("B006", "KTM", "Duke 200", 70.0));
        rentalSystem.addVehicle(new Bike("B007", "Suzuki", "Gixxer SF 250", 62.0));
        rentalSystem.addVehicle(new Bike("B008", "Harley-Davidson", "Street 750", 110.0));
        rentalSystem.addVehicle(new Bike("B009", "Hero", "Splendor Plus", 35.0));
        rentalSystem.addVehicle(new Bike("B010", "Kawasaki", "Ninja 300", 85.0));
        rentalSystem.addVehicle(new Bike("B011", "Bajaj", "Dominar 400", 75.0));
        rentalSystem.addVehicle(new Bike("B012", "Aprilia", "RS 660", 150.0));
        rentalSystem.addVehicle(new Bike("B013", "Benelli", "Imperiale 400", 55.0));
        rentalSystem.addVehicle(new Bike("B014", "TVS", "Ronin", 52.0));
        rentalSystem.addVehicle(new Bike("B015", "Revolt", "RV400", 60.0));
        rentalSystem.addVehicle(new Bike("B016", "Yezdi", "Roadster", 63.0));
        rentalSystem.addVehicle(new Bike("B017", "Jawa", "42", 68.0));
        rentalSystem.addVehicle(new Bike("B018", "Honda", "Hornet 2.0", 59.0));
        rentalSystem.addVehicle(new Bike("B019", "Bajaj", "Avenger Cruise 220", 54.0));
        rentalSystem.addVehicle(new Bike("B020", "Yamaha", "R15 V4", 67.0));
        rentalSystem.addVehicle(new Bike("B021", "TVS", "Raider", 48.0));
        rentalSystem.addVehicle(new Bike("B022", "KTM", "RC 390", 80.0));
        rentalSystem.addVehicle(new Bike("B023", "Royal Enfield", "Meteor 350", 64.0));
        
        rentalSystem.menu();
	}

}
