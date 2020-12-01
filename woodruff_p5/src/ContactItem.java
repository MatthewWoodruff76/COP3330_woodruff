public class ContactItem {
    private String first_name, last_name, phone_number, email_address;
    public ContactItem(String first, String last, String phone, String email) {
        first_name = first;
        last_name = last;
        phone_number = phone;
        email_address = email;
    }

    public static ContactItem newContact(String first_name, String last_name, String phone_number, String email_address) {
        if(first_name.length()+last_name.length()+phone_number.length()+email_address.length()==0) return null;
        if(!PhoneIsValid(phone_number)) return null;
        if(!EmailIsValid(email_address)) return null;
        return new ContactItem(first_name,last_name,phone_number,email_address);
    }
    static boolean ContactIsValid(String first_name, String last_name, String phone_number, String email_address){
        if(first_name.length()+last_name.length()+phone_number.length()+email_address.length()==0) return false;
        if(!PhoneIsValid(phone_number)) return false;
        if(!EmailIsValid(email_address)) return false;
        return true;
    }
    static boolean PhoneIsValid(String phone_number) {
        if(phone_number.length()!=0){
            if(phone_number.length()!=12) {
                System.out.print("\nYour phone number must be 12 units long.\n");
                return false;
            }
            String code[] = phone_number.split("-",0);
            if(code.length!=3) {
                System.out.print("\nYour phone number does not follow the proper format.\n");
                return false;
            }
            for(int index = 0; index < 3; index++){
                try{
                    Integer.parseInt(code[index]);
                } catch (NumberFormatException e) {
                    System.out.print("\nYour phone number contains invalid characters.\n");
                    return false;
                }
            }
        }
        return true;
    }
    static boolean EmailIsValid(String email_address) {
        if(email_address.length()!=0){
            String[] header = email_address.split("@",0);
            if(header.length!=2) {
                System.out.print("\nYour email is incomplete.\n");
                return false;
            }
            String[] footer = header[1].split("\\.", 0);
            if(footer.length!=2) {
                System.out.print("\nYour email is incomplete.\n");
                return false;
            }
        }
        return true;
    }

    public String getFirst(){
        return first_name;
    }
    public String getLast(){
        return last_name;
    }
    public String getPhone(){
        return phone_number;
    }
    public String getEmail(){
        return email_address;
    }
    public boolean setFirst(String first){
        first_name = first;
        return true;
    }
    protected boolean setLast(String last){
        last_name = last;
        return true;
    }
    protected boolean setPhone(String phone){
        if(PhoneIsValid(phone)){
            phone_number = phone;
            return true;
        }
        return false;
    }
    protected boolean setEmail(String email){
        if(first_name.length()+last_name.length()+phone_number.length()+email.length()==0) {
            System.out.print("You cannot have all blank values.\n");
            return false;
        }
        System.out.print("Your contact item has been set.\n");
        email_address = email;
        return true;
    }

    public String AmassContactInfo() {
        return  first_name + "\r\n" + last_name + "\r\n"
                + phone_number + "\r\n" + email_address + "\r\n" ;
    }
}
