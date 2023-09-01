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

    private static int fieldSizeX; // Размерность игрового поля
    private static int fieldSizeY; // Размерность игрового поля

    /**
     * Точка входа в программу
     * @param args вожможные аргументы программы
     */
    public static void main(String[] args) {
        initialize();
        printField();
        turnHuman();
        turnAI();

    }

    /**
     * Инициализация игрового поля
     */
    private static void initialize(){
        fieldSizeX = 5;
        fieldSizeY = 5;
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
    private static void turnHuman(){
        int x = 0, y = 0;
        do{
            System.out.println(String.format("Введите координаты хода X и Y (от %d до %d)",fieldSizeX,fieldSizeY));
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        }while (!isCellValid(x,y) || !isCellEmpty(x,y));
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
     * Проверки по горизонтали, вертикали, диагоналям
     * @param c
     * @return
     */
    private static boolean checkWin(char c){
        // время 1.13
        // Простой вариант проверки по горизонтали
        if (field[0][0] == c && field[0][1] == c && field[0][2] == c && field[0][3] == c && field[0][4] == c) return true;
        if (field[1][0] == c && field[1][1] == c && field[1][2] == c && field[1][3] == c && field[1][4] == c) return true;
        if (field[2][0] == c && field[2][1] == c && field[2][2] == c && field[2][3] == c && field[2][4] == c) return true;
        if (field[3][0] == c && field[3][1] == c && field[3][2] == c && field[3][3] == c && field[3][4] == c) return true;
        if (field[4][0] == c && field[4][1] == c && field[4][2] == c && field[4][3] == c && field[4][4] == c) return true;

        int count = 0;
        // Проверки по горизонтали
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                if(field[x][y] == c) count++;
            }
        }
        // Проверки по вертикали
        // Проверки по диагонали X
        for (int x = 0; x < fieldSizeX; x++) {
            if (field[x][x] == c) count++;
        }
        if (count == fieldSizeX) return true;

        // Проверка по диагонали Y
        count = 0;
        for (int y = 0; y < fieldSizeX; y++) {
            if (field[y][y] == c) count++;
        }
        if (count == fieldSizeY) return true;

        return false;
    }
}
