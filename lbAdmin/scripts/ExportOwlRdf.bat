@echo off
REM Exports content from the repository to a file in OWL/RDF format.
REM
REM Options:
REM   -out,--output <uri> URI or path of the directory to contain the
REM        resulting OWL file.  The file name will be automatically derived
REM        from the coding scheme name.   
REM   -u,--urn <name> URN of the coding scheme to export.
REM   -v,--version <id> The assigned tag/label or absolute version
REM        identifier of the coding scheme.
REM   -f,--force If specified, allows the destination file to be
REM        overwritten if present.
REM 
REM Note: If the coding scheme and version values are unspecified,
REM a list of available coding schemes will be presented for
REM user selection.
REM
REM Example: ExportOwlRdf -out "file:///path/to/dir" -f
REM Example: ExportOwlRdf -out "file:///path/to/dir" -u "NCI_Thesaurus" -v "PRODUCTION" -f
REM
java -Xmx1000m -cp "..\runtime\lbPatch.jar;..\runtime-components\extLib\*" org.LexGrid.LexBIG.admin.ExportOwlRdf %*
