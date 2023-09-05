import java.util.Random;
import java.util.Scanner;

/**
 * Игра в крестики-нолики
 */
public class Program {
    /**
     * Фишки игроков
     */
    private static final char DOT_NUMAN = 'X'; // Фишка игрока - человек
    private static final char DOT_AI = '0'; // Фишка игрока - компьютер
    private static final char DOT_EMPTY = '*';; // Признак пустого поля

    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();

    private static char[][] field; // Двумерный массив, хранит текущее состояние игрового поля
    private static int[] point = new int[2]; // Выбранные

    private static int fieldSizeX; // Размерность игрового поля
    private static int fieldSizeY; // Размерность игрового поля

    private static int minVictory;
    private static int areaSize;

    /**
     * Точка входа в программу
     * @param args вожможные аргументы программы
     */
    public static void main(String[] args) {
        areaSize = 5;
        int X = 5;
        int Y = 5;
        if (areaSize>3){
            minVictory = 4;
        }else{
            minVictory = areaSize;
        }

        initialize(X,Y);
        printField();
        while (true){
            turnHuman();
            printField();
            if (checkGameState(DOT_NUMAN)) {
                break;
            }
            turnAI();
            printField();
            if (checkGameState(DOT_AI)) {
                break;
            }
        }
    }

    /**
     * Инициализация игрового поля
     */
    private static void initialize(int X, int Y){
        fieldSizeX = X;
        fieldSizeY = Y;
        field = new char[fieldSizeX][fieldSizeY];
        for(int x = 0; x < fieldSizeX; x++){
            for (int y = 0; y < fieldSizeY; y++) {
                field[x][y] = DOT_EMPTY;
            }
        }
    }

    /**
     * Инициализация игрового поля
     *  +-1 2 3 4 5
     *  1|*|*|*|*|*
     *  2|*|*|*|*|*
     *  3|*|*|*|*|*
     *  4|*|*|*|*|*
     *  5|*|*|*|*|*
     *  -----------
     */
    private static void printField(){
        // header
        System.out.print("+");
        for (int x = 0; x < fieldSizeX*2+1; x++) {
            System.out.print((x%2 == 0) ? "-" : x/2+1);
        }
        System.out.println();
        //
        for (int x = 0; x < fieldSizeX; x++) {
            System.out.print(x+1+"|");
            for (int y = 0; y < fieldSizeY; y++) {
                System.out.print(field[x][y]+"|");
            }
            System.out.println();
        }

        // footer
        for (int x = 0; x < fieldSizeX*2+2; x++) {
            System.out.print("-");
        }
        System.out.println();
    }

    /**
     * Обработка хода человека
     */
    private static void turnHuman() {
        int x = 0, y = 0;
        do {
            while (true) {
                System.out.println(String.format("Введите координаты хода X (от 0 до %d)", fieldSizeX));
                if (scanner.hasNextInt()) {
                    x = scanner.nextInt() - 1;
                    scanner.nextLine();
                    break;
                } else {
                    System.out.println("Некорректное число, повторите попытку ввода");
                    scanner.nextLine();
                }
            }

            while(true){
                System.out.println(String.format("Введите координаты хода Y (от 0 до %d)", fieldSizeY));
                if (scanner.hasNextInt()) {
                    y = scanner.nextInt() - 1;
                    scanner.nextLine();
                    break;
                } else {
                    System.out.println("Некорректное число, повторите попытку ввода");
                    scanner.nextLine();
                }
            }

        }while (!isCellValid(x, y) || !isCellEmpty(x, y)) ;
        point[0] = x;
        point[1] = y;
        field[x][y] = DOT_NUMAN;
    }
    /**
     * Обработка хода компьютера
     */
    private static void turnAI(){
        int x = 0,y = 0;
        do{
            x = random.nextInt(fieldSizeX);
            y = random.nextInt(fieldSizeY);
        }while (!isCellEmpty(x,y));
        field[x][y] = DOT_AI;
    }

    /**
     * Проверка, является ли ячейка пустой (DOT_EMPTY)
     * @param x координата X
     * @param y координата Y
     * @return
     */
    private static boolean isCellEmpty(int x, int y){
        return field[x][y] == DOT_EMPTY;
    }

    /**
     * Провека корректности числового значения
     * (в рамках игрового поля)
     * @param x
     * @param y
     * @return
     */
    private static boolean isCellValid(int x, int y){
        return x>=0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    /**
     * Проверка состояния игры
     * @param c фишка игрока
     * @return
     */
    private static boolean checkGameState(char c){
        if(checkWin(c)){
            System.out.println(String.format("Игра закончена, выиграли %s",c));
            return true;
        }
        if(checkDraw()){
            System.out.println("Ничья");
            return true;
        }
        return false; // Игра продолжается
    }
    /**
     * Проверки Победы - по горизонтали, вертикали, диагоналям
     * @param c // фишка игрока
     * @return
     */
    private static boolean checkWin(char c){
        int X = point[0];
        int Y = point[1];

        // Проверка по вертикали в обе стороны
        int count = 0;
        for (int x = X; x < fieldSizeX; x++) {
            if(field[x][Y] == c){
                count++;
            }else{
                break;
            }
        }
        for (int x = X-1; x >= 0; x--) {
            if(field[x][Y] == c){
                count++;
            }else {
                break;
            }
        }
        if(count>=minVictory) return true;

        // Проверки по горизонтали
        count = 0;
        for (int y = Y; y < fieldSizeY; y++) {
            if(field[X][y] == c){
                count++;
            }else{
                break;
            }
        }
        for (int y = Y-1; y >= 0; y--) {
            if(field[X][y] == c){
                count++;
            }else {
                break;
            }
        }
        if(count>=minVictory) return true;

        // Проверки диагонали по X
        count = 0;
        int offset = X-Y; // шаг смещения
        for (int x = X; x < fieldSizeX; x++) {
            for (int y = Y; y < fieldSizeY; y++) {
                if(X-Y-offset == 0) {
                    if (field[x][y] == c) {
                        count++;
                    } else {
                        break;
                    }
                }
            }
            for (int y = Y-1; y >= 0 ; y--) {
                if(X-Y-offset == 0) {
                    if (field[x][y] == c) {
                        count++;
                    } else {
                        break;
                    }
                }
            }
        }
        if (count >= minVictory) return true;

        // Проверка диагонали по Y
        count = 0;
        for (int y = Y; y < fieldSizeY; y++) {
            for (int x = X; x < fieldSizeX; x++) {
                if(X-Y-offset == 0) {
                    if (field[x][y] == c) {
                        count++;
                    } else {
                        break;
                    }
                }
            }
            for (int x = X-1; x >= 0 ; x--) {
                if(X-Y-offset == 0) {
                    if (field[x][y] == c) {
                        count++;
                    } else {
                        break;
                    }
                }
            }
        }
        if (count == fieldSizeX) return true;

        return false;
    }

    /**
     * Проверка на ничью
     * @return метод вернет истину, если ничья
     */
    private static boolean checkDraw(){
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                if(isCellEmpty(x,y)) return false;
            }
        }
        return true;
    }
}
