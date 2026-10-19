from data_transformer import *
from model import *

import pickle

import os
from flask import Flask, request, jsonify

resources_path = os.getcwd() + '/resources'
model_path = resources_path + "/Random forest.joblib"
data_transformer_path = resources_path + '/data_transformer.pkl'

model = Model(model_path)
with open(data_transformer_path, 'rb') as file:
    data_transformer = pickle.load(file)

app = Flask(__name__)
app.secret_key = 'your_secret_key'


@app.route('/loan_application', methods=['POST'])
def loan_application():
    if not request.is_json:
        return jsonify({"error": "Missing JSON in request"}), 400

    profile_json = request.get_json()
    print("Incoming JSON:", profile_json)

    processed_profile = data_transformer.process_profile(profile_json)
    print("Incoming DataFrame:", processed_profile)

    loan_status = model.predict(processed_profile)
    print("Prediction result:", loan_status)

    return jsonify({"loan_status": loan_status}), 200


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8888)
