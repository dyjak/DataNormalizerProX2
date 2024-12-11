import pandas as pd
from sklearn.impute import KNNImputer
import sys

def fill_missing_knn(data_path, output_path, n_neighbors):
    try:
        # Wczytaj dane
        df = pd.read_csv(data_path)

        # Tworzenie KNNImputer
        imputer = KNNImputer(n_neighbors=int(n_neighbors))

        # Uzupełnianie danych
        filled_data = imputer.fit_transform(df.select_dtypes(include=[float, int]))

        # Przywracanie do DataFrame
        df_filled = pd.DataFrame(filled_data, columns=df.select_dtypes(include=[float, int]).columns)

        # Jeśli są inne kolumny (np. tekstowe), łączymy je z uzupełnionymi danymi
        for col in df.columns:
            if col not in df_filled.columns:
                df_filled[col] = df[col]

        # Zapis do pliku
        df_filled.to_csv(output_path, index=False)
        print(f"Uzupełnione dane zapisano do: {output_path}")
    except Exception as e:
        print(f"Błąd: {e}")

if __name__ == "__main__":
    if len(sys.argv) != 4:
        print("Użycie: python script.py <ścieżka_wejściowa> <ścieżka_wyjściowa> <liczba_sąsiadów>")
    else:
        fill_missing_knn(sys.argv[1], sys.argv[2], sys.argv[3])
