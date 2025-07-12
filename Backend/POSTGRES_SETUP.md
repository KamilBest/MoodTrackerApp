# PostgreSQL Setup (Windows)

Quick setup for local development on Windows.

## Install PostgreSQL

1. Download from [postgresql.org](https://www.postgresql.org/download/windows/)
2. Run the installer
3. Set password for `postgres` user (remember this!)

## Create Database

1. Open **pgAdmin** (installed with PostgreSQL)
2. Connect to server using your `postgres` password
3. Right-click **Databases** → **Create** → **Database**
4. Name: `moodtracker`
5. Right-click **Login/Group Roles** → **Create** → **Login/Group Role**
6. Name: `mood`, Password: `mood123`
7. Go to **Privileges** tab → **Can login?** = Yes
8. Right-click **moodtracker** database → **Properties** → **Security** → **Add** → Select `mood` user

## Test Connection

```bash
psql -h localhost -U mood -d moodtracker
# Password: mood123
``` 