#!/usr/bin/python
# -*- coding: utf-8 -*-

import re

import os
from subprocess import Popen, PIPE

def solve_it(input_data, name):

    # Writes the inputData to a temporay file
    name = re.findall(r"[\w']+", name)[-1]
    tmp_file_name = 'tmp.data' + name
    tmp_file = open(tmp_file_name, 'w')
    tmp_file.write(input_data)
    tmp_file.close()

    # Runs the command: java Solver -file=tmp.data

    process = Popen(['java', '-Xmx1024m', '-cp', '.;./guava-16.0.1.jar;choco-solver-3.1.1-jar-with-dependencies.jar', 'Solver', '-file=' + tmp_file_name], stdout=PIPE)
    (stdout, stderr) = process.communicate()

    # removes the temporay file
    os.remove(tmp_file_name)

    return stdout.strip()


import sys

if __name__ == '__main__':
    if len(sys.argv) > 1:
        file_location = sys.argv[1].strip()
        name = re.findall(r"[\w']+", file_location)[-1]
        input_data_file = open(file_location, 'r')
        input_data = ''.join(input_data_file.readlines())
        input_data_file.close()
        print solve_it(input_data, name)
    else:
        print 'This test requires an input file.  Please select one from the data directory. (i.e. python solver.py ./data/ks_4_0)'

