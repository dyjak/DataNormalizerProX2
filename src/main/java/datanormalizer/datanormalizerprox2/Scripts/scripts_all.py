import pandas as pd
from sklearn.impute import KNNImputer
from sklearn.cluster import KMeans
from sklearn.ensemble import IsolationForest
from sklearn.preprocessing import MinMaxScaler, StandardScaler
from sklearn.decomposition import PCA
import sys
import os

def validate_file_path(file_path):
    if not os.path.exists(file_path):
        raise FileNotFoundError(f"Plik wejściowy nie istnieje: {file_path}")

def validate_numeric_columns(df):
    numeric_cols = df.select_dtypes(include=[float, int]).columns
    if len(numeric_cols) == 0:
        raise ValueError("Plik nie zawiera kolumn numerycznych do przetworzenia.")
    return numeric_cols

def fill_missing_knn(data_path, output_path, n_neighbors):
    try:
        validate_file_path(data_path)
        df = pd.read_csv(data_path)
        numeric_cols = validate_numeric_columns(df)

        imputer = KNNImputer(n_neighbors=int(n_neighbors))
        filled_data = imputer.fit_transform(df[numeric_cols])
        df_filled = pd.DataFrame(filled_data, columns=numeric_cols)

        for col in df.columns:
            if col not in df_filled.columns:
                df_filled[col] = df[col]

        df_filled.to_csv(output_path, index=False)
        print(f"Dane zostały zapisane do: {output_path}")

    except Exception as e:
        print(f"Błąd: {e}")

def cluster_data(data_path, output_path, n_clusters):
    try:
        validate_file_path(data_path)
        df = pd.read_csv(data_path)
        numeric_cols = validate_numeric_columns(df)

        kmeans = KMeans(n_clusters=int(n_clusters), random_state=42)
        df['cluster'] = kmeans.fit_predict(df[numeric_cols])

        df.to_csv(output_path, index=False)
        print(f"Dane z przypisanymi klastrami zapisano do: {output_path}")

    except Exception as e:
        print(f"Błąd: {e}")

def detect_outliers(data_path, output_path, contamination):
    try:
        validate_file_path(data_path)
        df = pd.read_csv(data_path)
        numeric_cols = validate_numeric_columns(df)

        isolation_forest = IsolationForest(contamination=float(contamination), random_state=42)
        df['is_outlier'] = isolation_forest.fit_predict(df[numeric_cols])

        df.to_csv(output_path, index=False)
        print(f"Wyniki detekcji wartości odstających zapisano do: {output_path}")

    except Exception as e:
        print(f"Błąd: {e}")

def standardize_data(data_path, output_path, method):
    try:
        validate_file_path(data_path)
        df = pd.read_csv(data_path)

        if method not in ['minmax', 'standard']:
            raise ValueError("Niepoprawna metoda. Użyj 'minmax' lub 'standard'.")

        numeric_cols = validate_numeric_columns(df)

        scaler = MinMaxScaler() if method == 'minmax' else StandardScaler()
        scaled_data = scaler.fit_transform(df[numeric_cols])

        df_scaled = pd.DataFrame(scaled_data, columns=numeric_cols)

        for col in df.columns:
            if col not in numeric_cols:
                df_scaled[col] = df[col]

        df_scaled.to_csv(output_path, index=False)
        print(f"Przekształcone dane zapisano do: {output_path}")

    except Exception as e:
        print(f"Błąd: {e}")

def reduce_dimensions(data_path, output_path, n_components):
    try:
        validate_file_path(data_path)
        df = pd.read_csv(data_path)
        numeric_cols = validate_numeric_columns(df)

        pca = PCA(n_components=int(n_components))
        reduced_data = pca.fit_transform(df[numeric_cols])
        df_reduced = pd.DataFrame(reduced_data, columns=[f"PC{i+1}" for i in range(int(n_components))])

        for col in df.columns:
            if col not in numeric_cols:
                df_reduced[col] = df[col]

        df_reduced.to_csv(output_path, index=False)
        print(f"Zredukowane dane zapisano do: {output_path}")

    except Exception as e:
        print(f"Błąd: {e}")

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Brak wystarczających argumentów. Użyj jednego z trybów: knn, kmeans, forest, standardize, pca.")
    else:
        mode = sys.argv[1].lower()
        args = sys.argv[2:]
        if mode == "knn" and len(args) == 3:
            fill_missing_knn(*args)
        elif mode == "kmeans" and len(args) == 3:
            cluster_data(*args)
        elif mode == "forest" and len(args) == 3:
            detect_outliers(*args)
        elif mode == "standardize" and len(args) == 3:
            standardize_data(*args)
        elif mode == "pca" and len(args) == 3:
            reduce_dimensions(*args)
        else:
            print("Niepoprawne argumenty lub tryb.")
