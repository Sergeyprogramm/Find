/**
 * Created by 1081_1 on 16.05.17.
 */
public class SearchResult {
    String path;
    boolean found;

    public SearchResult()
    {
        path ="";
        found=false;
    }

    public SearchResult(String _path, boolean _found) {
        path = _path;
        found = _found;
    }

    public String Print()
    {
        if(found)
            return "Файл найден: "+path;
        else
            return "Файл не найден";
    }
}
