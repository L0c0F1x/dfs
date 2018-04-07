# dfs
DFS - Duplicate File Searcher

Searches for duplicate files by hashing each file in a given directory tree and produces a map of these hashes and their corresponding files.
The map is then printed out into an .ods file for further analyzation. This is accomplished using the jOpenDocument library available at http://www.jopendocument.org/

MULTITHREADING:
The program is multithreaded, so should provide results in a reasonable amount of time for most sizes of a typical private file collection. The amount of threads used may be configured (defaults to 4).
