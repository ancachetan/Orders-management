package start;

import dao.AbstractDAO;

import javax.swing.*;
import java.lang.reflect.Field;
import java.util.List;

public class CreateTable<T> {

    public CreateTable(List<T> list) throws IllegalAccessException {
        JFrame frame = new JFrame();
        frame.setVisible(true);

        int n = ReflectionRetrieveProperties.retrieveProperties(list.get(0)).size();
        String[] header = new String[n + 1];

        int i = 0;
        while (i < n){
            header[i] = ReflectionRetrieveProperties.retrieveProperties(list.get(0)).get(i);
            //System.out.println(header[i]);
            i++;
        }

        int nrlinii = list.size();
        String[][] data = new String[nrlinii + 1][n + 1];

        i = 0;
        int j = 0;

        for (T t : list){
            j = 0;
            for (Field field : t.getClass().getDeclaredFields()){
                field.setAccessible(true);

                Object value = field.get(t);
                data[i][j] = value.toString();
                j++;
            }
            i++;
        }

        JTable jt = new JTable(data, header);

        jt.setBounds(30, 40, 200, 300);

        JScrollPane sp = new JScrollPane(jt);
        frame.add(sp);

    }
}
