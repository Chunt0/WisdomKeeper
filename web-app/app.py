import csv
import pandas as pd
from flask import Flask, render_template, jsonify, request

app = Flask(__name__)

def get_questions():
    questions = []
    with open('../dataset/data.csv', 'r') as file:
        reader = csv.reader(file)
        for row in reader:
            questions.append((row[0],row[1]))
    return questions

def update_question(index):
    # Read the CSV file into a DataFrame
    df = pd.read_csv('../dataset/data.csv')

    # Update the specific cell
    df.at[index, 'column_name'] = 1 # Replace 'column_name' with the name of the column you want to modify

    # Write the DataFrame back to the CSV file
    df.to_csv('../dataset/data.csv', index=False)

@app.route('/')
def index():
    questions = get_questions()
    return render_template('index.html', questions=questions)

@app.route('/update_question', methods=['POST'])
def update():
    index = request.json['index']
    update_question(index)
    return jsonify(success=True)

if __name__ == '__main__':
    app.run(debug=True)
