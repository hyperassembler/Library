package Java.Library;
public class JSONDiff
{
    public static void main(String args[])
    {

    }
    private static boolean deepCompareJSON(String jsonStringA, String jsonStringB) {
        if (jsonStringA == null || jsonStringB == null)
            return false;
        boolean isEqual = false;
        Map<String, String> jsonMapA = buildResponseMap(jsonStringA);
        Map<String, String> jsonMapB = buildResponseMap(jsonStringB);
        if (jsonMapA == null || jsonMapB == null) {
            if (jsonMapA == null && jsonMapB == null) {
                List<String> arrayA = parseJSONArray(jsonStringA);
                List<String> arrayB = parseJSONArray(jsonStringB);
                if (arrayA == null || arrayB == null) {
                    if (arrayA == null && arrayB == null) {
                        return jsonStringA.equals(jsonStringB);
                    } else {
                        return false;
                    }
                }
                arrayA.removeAll(arrayB);
                return arrayA.size() == 0;
            } else {
                return false;
            }
        }

        if (jsonMapA.size() != jsonMapB.size())
            return false;
        for (Map.Entry<String, String> eachMap : jsonMapA.entrySet()) {
            final String jsonB = jsonMapB.get(eachMap.getKey());
            if (jsonB == null)
                return false;
            else {
                isEqual = deepCompareJSON(eachMap.getValue(), jsonMapB.get(eachMap.getKey()));
                if (!isEqual)
                    return false;
            }
        }
        return isEqual;
    }
}