/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.cput.filehandlingapp;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 *
 * @author TylerYorkeFredericks_218047894
 */
public class FileReaderApp {

    ObjectInputStream input;
    ArrayList<Customer> customer = new ArrayList<>();
    ArrayList<Supplier> supplier = new ArrayList<>();
    int Year = Calendar.getInstance().get(Calendar.YEAR);
    int age[];
    int rent;
    int notrent;

    public void openFile() {
        try {
            input = new ObjectInputStream(new FileInputStream("stakeholder.ser"));
            System.out.println("ser file opened");
        } catch (IOException ioe) {
            System.out.println("error opening ser file: " + ioe.getMessage());
            System.exit(1);
        }
    }

    public void closeFile() {
        try {
            input.close();
        } catch (IOException ioe) {
            System.out.println("error opening ser file: " + ioe.getMessage());
        }

    }

    public void generateArrayListV2() throws IOException {

        Stakeholder stakes;

        try {

            while (true) {
                stakes = (Stakeholder) input.readObject();
                if (stakes instanceof Customer) {
                    customer.add((Customer) stakes);
                } else if (stakes instanceof Supplier) {
                    supplier.add((Supplier) stakes);
                }
            }
        } catch (EOFException eof) {
            System.out.println("End of File Reached: " + eof.getMessage());
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Class Not Found Exception: " + cnfe.getMessage());
        } catch (IOException ioe) {
            System.out.println("IOException: " + ioe.getMessage());
        }
    }

    public void outputCustomer() {
        for (int i = 0; i < customer.size(); i++) {
            System.out.println(customer.get(i));
        }
    }

    public void outputSupplier() {
        for (int i = 0; i < supplier.size(); i++) {
            System.out.println(supplier.get(i));
        }
    }

    public void customerSort() {
        customer.sort(Comparator.comparing(Stakeholder::getStHolderId));
    }

    public void sortSupplier() {
        supplier.sort(Comparator.comparing(Supplier::getName));
    }

    public void Age() {
        age = new int[customer.size()];
        for (int i = 0; i < customer.size(); i++) {
            LocalDate ld = LocalDate.parse(customer.get(i).getDateOfBirth());
            int year = ld.getYear();
            age[i] = 2021 - year;
        }
    }

    public void canRent() {
        for (int i = 0; i < customer.size(); i++) {
            if (customer.get(i).getCanRent() == true) {
                rent++;
            } else if (customer.get(i).getCanRent() == false) {
                notrent++;
            }
        }
    }

    public void customerWrite() {
        try {
            FileWriter writer = new FileWriter("Customer.txt");
            BufferedWriter bw = new BufferedWriter(writer);

            bw.write("=====================CUSTOMERS=====================");
            bw.newLine();

            bw.write("ID\tName\tSurname\tDate of Birth\tAge");
            bw.newLine();

            bw.write("===================================================");
            bw.newLine();

            for (int i = 0; i < customer.size(); i++) {

                bw.write(customer.get(i).getStHolderId() + "\t" + customer.get(i).getFirstName() + "\t" + customer.get(i).getSurName() + "\t" + customer.get(i).getDateOfBirth() + "\t" + age[i]);
                bw.newLine();

            }

            bw.write("===================================================");
            bw.newLine();

            bw.write("Number of Customers who can rent: " + rent);
            System.out.println("Number of Customers who can rent: " + rent);
            bw.newLine();

            bw.write("Number of Customers who cannot rent: " + notrent);
            System.out.println("Number of Customers who cannot rent: " + notrent);
            bw.newLine();

            bw.close();
        } catch (IOException ioe) {
            System.out.println("IOException: " + ioe.getMessage());
        }

    }

    public void supplierWrite() {
        try {
            FileWriter writer = new FileWriter("Supplier.txt");
            BufferedWriter bw = new BufferedWriter(writer);

            bw.write("=======================SUPPLIER=======================");
            bw.newLine();

            bw.write("ID\tName\tProd Type\tDescription");
            bw.newLine();

            bw.write("======================================================");
            bw.newLine();

            for (int i = 0; i < supplier.size(); i++) {
                bw.write(supplier.get(i).getStHolderId() + "\t" + supplier.get(i).getName() + "\t" + supplier.get(i).getProductType() + "\t" + supplier.get(i).getProductDescription());
                bw.newLine();
            }
            bw.close();
        } catch (IOException ioe) {
            System.out.println("error opening ser file: " + ioe.getMessage());
        }

    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        FileReaderApp obj = new FileReaderApp();
        obj.openFile();
        obj.generateArrayListV2();
        obj.Age();
        obj.canRent();
        obj.outputCustomer();
        obj.outputSupplier();
        obj.customerSort();
        obj.closeFile();
        obj.customerWrite();
        obj.supplierWrite();
    }

}
