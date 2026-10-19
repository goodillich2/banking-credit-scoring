import joblib
import pandas as pd


class Model:
    def __init__(self, model_path):
        self.models = joblib.load(model_path)

    def predict(self, profile_dict: dict) -> str:
        profile = pd.DataFrame.from_dict(profile_dict, orient='index').T
        prediction_result = self.models.predict(profile)

        if prediction_result == 1:
            result = "accepted"
        else:
            result = "denied"

        return result
