# EC-007 — Development environment

## Commands

```powershell
.\scripts\development\platform.ps1 config
.\scripts\development\platform.ps1 start
.\scripts\development\platform.ps1 status
.\scripts\development\platform.ps1 logs -Service kafka
.\scripts\development\platform.ps1 stop
.\scripts\development\platform.ps1 test
```

Maven services should use `-Dproject.build.directory=$PWD/build-output` when Windows permissions lock an existing `target` directory. Build output is ignored by Git.
