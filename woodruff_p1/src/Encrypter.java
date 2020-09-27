public class Encrypter {
    public String encrypt(String args) {
        //converts the string to its integer value and initializes the working array
        int data = Integer.parseInt(args);
        int[] temp = {0, 0, 0, 0};
        //puts the data into an array in the same order and sets data to 0
        temp = int2Array(data, temp);
        //performs the first step of encryption
        temp = step1(temp);
        //performs the second step of encryption
        temp = step2(temp);
        //converts back to integer value
        data = Array2int(data, temp);
        //converts back to a string
        args = Integer.toString(data);
        //adds leading zeros
        args = leadingZeros(args);
        return args;
    }
    public static int[] int2Array(int data, int[] temp){
        for (int i = 0; i < 4; i++) {
            temp[3 - i] = data % 10;
            data = data / 10;
        }
        return temp;
    }
    public static int[] step1(int[] args) {
        for (int i = 0; i < 4; i++) {
            args[i] = (args[i] + 7) % 10;
        }
        return args;
    }
    public static int[] step2(int[] temp) {
        int passover;
        passover = temp[0];
        temp[0] = temp[2];
        temp[2] = passover;
        passover = temp[1];
        temp[1] = temp[3];
        temp[3] = passover;
        return temp;
    }
    public static int Array2int(int data, int[] temp){
        data = temp[0] * 1000 + temp[1] * 100 + temp[2] * 10 + temp[3];
        return data;
    }
    public static String leadingZeros(String args) {
        while (args.length() < 4) {
            args = "0" + args;
        }
        return args;
    }
}
